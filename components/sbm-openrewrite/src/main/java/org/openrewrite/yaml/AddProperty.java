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
package org.openrewrite.yaml;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.openrewrite.*;
import org.openrewrite.marker.Markers;
import org.openrewrite.yaml.tree.Yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = true)
public class AddProperty extends Recipe {

    @Option(displayName = "Property key", example = "server.name")
    private String key;

    @Option(displayName = "Property value", example = "server-name")
    private String value;

    @Override
    public String getDisplayName() {
        return "Add yaml property.";
    }

    @Override
    public String getDescription() {
        return "Add a property key value pair at the end.";
    }

    @Override
    protected TreeVisitor<?, ExecutionContext> getVisitor() {
        return new AddPropertyVisitor();
    }

    public class AddPropertyVisitor<P> extends YamlVisitor<P> {

        public Yaml.Mapping.Entry visitMappingEntry(Yaml.Mapping.Entry entry, P p) {
            return entry;
        }

        @Override
        public Yaml visitMapping(Yaml.Mapping mapping, P p) {
            Yaml yaml = super.visitMapping(mapping, p);
            Yaml.Mapping.Entry entry = new Yaml.Mapping.Entry(
                                                            UUID.randomUUID(),
                                                            "",
                                                            Markers.EMPTY,
                                                           new Yaml.Scalar(UUID.randomUUID(),
                                                                           "", Markers.EMPTY,
                                                                           Yaml.Scalar.Style.PLAIN,
                                                                           null,
                                                                           key),
                                                              "",
                                                              new Yaml.Sequence(
                                                                      Tree.randomId(),
                                                                      Markers.EMPTY,
                                                                      null,
                                                                      List.of(
                                                                              new Yaml.Sequence.Entry(
                                                                                      Tree.randomId(),
                                                                                      " ",
                                                                                      Markers.EMPTY,
                                                                                      null,
                                                                                      false,
                                                                                      null
                                                                              ),
                                                                              new Yaml.Sequence.Entry(
                                                                                      Tree.randomId(),
                                                                                      value,
                                                                                      Markers.EMPTY,
                                                                                      null,
                                                                                      false,
                                                                                      null
                                                                              )
                                                                      ),
                                                                      null,
                                                                      null));
            List<Yaml.Mapping.Entry> entries = ((Yaml.Mapping) yaml).getEntries();
            List newEntries = new ArrayList(entries);
            newEntries.add(entry);
            Yaml.Mapping mapping1 = ((Yaml.Mapping) yaml).withEntries(newEntries);
            return mapping1;
        }

        @Override
        public Yaml visitSequence(Yaml.Sequence sequence, P p) {
            return super.visitSequence(sequence, p);
        }

        @Override
        public Yaml visitAnchor(Yaml.Anchor anchor, P p) {
            return super.visitAnchor(anchor, p);
        }

        @Override
        public Yaml visitDocuments(Yaml.Documents documents, P p) {
            return super.visitDocuments(documents, p);
        }
    }
}
