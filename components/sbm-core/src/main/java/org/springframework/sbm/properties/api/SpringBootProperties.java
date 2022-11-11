package org.springframework.sbm.properties.api;

import org.openrewrite.Recipe;

import java.util.Optional;

/**
 * @author Fabian Krüger
 */
public interface SpringBootProperties {
    void setProperty(String comment, String propertyName, String propertyValue);

    void setProperty(String key, String value);

    void renameProperty(String oldProperyName, String newPropertyName);

    Optional<String> getProperty(String key);

    java.util.Properties getProperties();

    void apply(Recipe r);
}
