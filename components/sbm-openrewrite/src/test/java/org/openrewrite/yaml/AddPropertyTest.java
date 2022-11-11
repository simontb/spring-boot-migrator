package org.openrewrite.yaml;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openrewrite.RecipeRun;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;
import org.openrewrite.test.SourceSpecs;
import org.openrewrite.yaml.tree.Yaml;

import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Fabian Krüger
 */
class AddPropertyTest {
    @Test
    void addYamlPropertyToEmptyFile() {
        String yamlProperties = "";
        List<Yaml.Documents> documents = new YamlParser().parse(yamlProperties);
        RecipeRun result = new AddProperty("a", "b").run(documents);
        assertThat(result.getResults().get(0).getAfter().printAll()).isEqualTo("a: b");
    }

    @Test
    void addYamlPropertyToFileWithProperties() {
        String yamlProperties = "a.b: c";
        List<Yaml.Documents> documents = new YamlParser().parse(yamlProperties);
        RecipeRun result = new AddProperty("a.b.c", "d").run(documents);
        assertThat(result.getResults().get(0).getAfter().printAll()).isEqualTo("""
                                                                               a.b: c
                                                                               a.b.c: d
                                                                               """);
    }
}