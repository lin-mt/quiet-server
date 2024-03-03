/*
 * Copyright (C) 2023 lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package cn.linmt.quiet.converter;

import static net.bytebuddy.description.annotation.AnnotationDescription.Builder.ofType;
import static net.bytebuddy.description.type.TypeDescription.Generic.Builder.parameterizedType;
import static net.bytebuddy.implementation.FieldAccessor.ofField;
import static net.bytebuddy.matcher.ElementMatchers.isDefaultConstructor;
import static net.bytebuddy.matcher.ElementMatchers.named;

import cn.linmt.quiet.modal.jpa.enums.AttributeConverterInterceptor;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.lang.reflect.Modifier;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.MethodDelegation;

public class AttributeConverterAutoGenerator {

  /** Auto generation suffix. */
  private static final String AUTO_GENERATION_SUFFIX = "$AttributeConverterGeneratedByByteBuddy";

  private final ClassLoader classLoader;

  private final Class<?> valueClass;

  public AttributeConverterAutoGenerator(ClassLoader classLoader, Class<?> valueClass) {
    this.classLoader = classLoader;
    this.valueClass = valueClass;
  }

  public <T> Class<?> generate(Class<T> clazz) {
    try {
      DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition<?> intercept =
          new ByteBuddy()
              .with(
                  new NamingStrategy.AbstractBase() {
                    @Override
                    @SuppressWarnings("NullableProblems")
                    protected String name(TypeDescription superClass) {
                      return clazz.getName() + AUTO_GENERATION_SUFFIX;
                    }
                  })
              .subclass(parameterizedType(AttributeConverter.class, clazz, valueClass).build())
              .annotateType(ofType(Converter.class).define("autoApply", true).build())
              .constructor(isDefaultConstructor())
              .intercept(
                  MethodCall.invoke(Object.class.getDeclaredConstructor())
                      .andThen(ofField("enumType").setsValue(clazz)))
              .defineField("enumType", Class.class, Modifier.PRIVATE | Modifier.FINAL)
              .method(named("convertToDatabaseColumn"))
              .intercept(MethodDelegation.to(AttributeConverterInterceptor.class))
              .method(named("convertToEntityAttribute"))
              .intercept(MethodDelegation.to(AttributeConverterInterceptor.class));
      ClassLoadingStrategy.Configurable<ClassLoader> classLoaderConfigurable =
          ClassLoadingStrategy.Default.INJECTION.allowExistingTypes();
      try (DynamicType.Unloaded<?> make = intercept.make()) {
        return make.load(this.classLoader, classLoaderConfigurable).getLoaded();
      }
    } catch (NoSuchMethodException e) {
      // should never happen
      throw new RuntimeException("Failed to get declared constructor.", e);
    }
  }
}
