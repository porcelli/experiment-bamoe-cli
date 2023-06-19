/**
 * Copyright 2023 IBM Corp.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.bamoe.cli;

import static java.util.List.of;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.model.Dependency;

public enum Features {
    BPMN(of(new Dependency() {{ setGroupId("org.kie.kogito"); setArtifactId("kogito-quarkus");}}),
            new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-resteasy"); }},
            new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-resteasy-jackson"); }},
            new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-arc"); }},
            new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-smallrye-openapi"); }}), 
    DMN(of(new Dependency() {{ setGroupId("org.kie.kogito"); setArtifactId("kogito-quarkus-decisions");}},
           new Dependency() {{ setGroupId("org.kie.kogito"); setArtifactId("kogito-scenario-simulation"); setScope("test");}}),
           new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-resteasy"); }},
           new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-resteasy-jackson"); }},
           new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-arc"); }},
           new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-smallrye-health"); }},
           new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-smallrye-health"); }}),
    DRL(of(new Dependency() {{ setGroupId("org.kie.kogito"); setArtifactId("kogito-quarkus-rules");}}),
           new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-resteasy"); }},
           new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-resteasy-jackson"); }},
           new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-arc"); }},
           new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-smallrye-openapi"); }}), 
    CLASSIC_DRL(of(new Dependency() {{ setGroupId("org.kie.kogito"); setArtifactId("kogito-quarkus-rules");}},
                   new Dependency() {{ setGroupId("org.kie.kogito"); setArtifactId("kogito-drools");}}),
                   new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-resteasy"); }},
                   new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-resteasy-jackson"); }},
                   new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-arc"); }},
                   new Dependency() {{ setGroupId("io.quarkus"); setArtifactId("quarkus-smallrye-openapi"); }});

    private final List<Dependency> dependencies = new ArrayList<>();
    private final List<Dependency> quarkusDependencies = new ArrayList<>();

    Features(final List<Dependency> dependencies, final Dependency... quarkusDependencies) {
        this.dependencies.addAll(dependencies);
        this.quarkusDependencies.addAll(Arrays.asList(quarkusDependencies));
    }

    List<Dependency> getDepedencies() {
        return dependencies;
    }

    List<Dependency> getQuarkusDepedencies() {
        return dependencies;
    }
}
