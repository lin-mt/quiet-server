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

package com.github.quiet.json.module;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.quiet.json.modifier.ServiceDeserializerModifier;
import com.github.quiet.json.modifier.ServiceSerializerModifier;

/**
 * ServiceSimpleModule.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class ServiceSimpleModule extends SimpleModule {

  public ServiceSimpleModule(String name) {
    super(name);
  }

  @Override
  public void setupModule(SetupContext context) {
    super.setupModule(context);
    context.addBeanDeserializerModifier(new ServiceDeserializerModifier());
    context.addBeanSerializerModifier(new ServiceSerializerModifier());
  }
}
