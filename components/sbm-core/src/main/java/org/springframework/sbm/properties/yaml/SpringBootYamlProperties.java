/*
 * Copyright 2021 - 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.sbm.properties.yaml;

import org.openrewrite.Recipe;
import org.openrewrite.internal.lang.Nullable;
import org.openrewrite.yaml.AddProperty;
import org.openrewrite.yaml.ChangeValue;
import org.openrewrite.yaml.search.FindProperty;
import org.openrewrite.yaml.tree.Yaml;
import org.springframework.sbm.project.resource.RewriteSourceFileHolder;
import org.springframework.sbm.properties.api.SpringBootProperties;

import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;

/**
 * @author Fabian Krüger
 */
public class SpringBootYamlProperties extends RewriteSourceFileHolder<Yaml.Documents> implements SpringBootProperties {
    /**
     * @param absoluteProjectDir the absolute path to project root
     * @param sourceFile         the OpenRewrite {@code SourceFile}
     */
    public SpringBootYamlProperties(Path absoluteProjectDir, Yaml.Documents sourceFile) {
        super(absoluteProjectDir, sourceFile);
    }

    @Override
    public void setProperty(String comment, String propertyName, String propertyValue) {
        if (FindProperty.find(getSourceFile(), propertyName, false).isEmpty()) {
            apply(new AddProperty(propertyName, propertyValue));
        } else {
            @Nullable String fileMatcher = this.getSourceFile().toString();
            apply(new ChangeValue(propertyName, propertyValue, fileMatcher));
        }
    }

    @Override
    public void setProperty(String key, String value) {

    }

    @Override
    public void renameProperty(String oldProperyName, String newPropertyName) {

    }

    @Override
    public Optional<String> getProperty(String key) {
        return Optional.empty();
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public void apply(Recipe r) {

    }
}
