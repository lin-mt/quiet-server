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

package cn.linmt.quiet.config;

import cn.linmt.quiet.converter.AutoGenerateConverterPersistenceUnitPostProcessor;
import cn.linmt.quiet.converter.EnumType;
import cn.linmt.quiet.properties.EnumScanPath;
import cn.linmt.quiet.properties.JpaEnumProperties;
import cn.linmt.quiet.util.ClassUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

// @Configuration
@EnableConfigurationProperties(JpaEnumProperties.class)
public class AutoGenerateEnumConverter {

  private final JpaEnumProperties jpaEnumProperties;

  public AutoGenerateEnumConverter(JpaEnumProperties jpaEnumProperties) {
    this.jpaEnumProperties = jpaEnumProperties;
  }

  @Bean
  public EntityManagerFactoryBuilderCustomizer entityManagerFactoryBuilderCustomizer(
      List<EnumScanPath> enumScanPaths) {
    Optional<List<String>> packageToScan = Optional.ofNullable(jpaEnumProperties.getPackages());
    List<String> paths =
        packageToScan.orElseGet(
            () -> {
              Class<?> mainClass = ClassUtils.deduceMainClass();
              if (mainClass != null) {
                return List.of(mainClass.getPackageName());
              }
              return List.of();
            });
    if (CollectionUtils.isNotEmpty(enumScanPaths)) {
      for (EnumScanPath enumScanPath : enumScanPaths) {
        if (StringUtils.isNotBlank(enumScanPath.getPath())) {
          paths.add(enumScanPath.getPath());
        }
        if (CollectionUtils.isNotEmpty(enumScanPath.getPaths())) {
          for (String path : enumScanPath.getPaths()) {
            if (StringUtils.isNotBlank(path)) {
              paths.add(path);
            }
          }
        }
      }
    }
    Optional<List<EnumType>> customerEnumTypes = Optional.ofNullable(jpaEnumProperties.getTypes());
    return builder ->
        builder.setPersistenceUnitPostProcessors(
            new AutoGenerateConverterPersistenceUnitPostProcessor(
                paths,
                customerEnumTypes.orElseGet(
                    () -> Arrays.stream(EnumType.values()).collect(Collectors.toList()))));
  }
}
