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

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;

@TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = {CreateProjectCommand.class, AddToProjectCommand.class})
public class ProjectCommand {
}

enum Runtime {
    Quarkus//, SpringBoot, RHBQ
}

@CommandLine.Command(name = "create-project", aliases = {"-c", "--create"})
class CreateProjectCommand implements Callable<Integer> {

    @CommandLine.Option(names = {"-p", "--path"}, description = "Path to the folder where project will be created.")
    File file;

    @CommandLine.Option(names = {"-c", "--component"}, description = "BAMOE Component (PAM or DM)", defaultValue = "PAM")
    ComponentType componentType;

    @CommandLine.Option(names = {"--groupId"}, description = "Group ID of the project", required = true)
    String groupId;

    @CommandLine.Option(names = {"--artifactId"}, description = "Artifact ID of the project", required = true)
    String artifactId;

    @CommandLine.Option(names = {"--version"}, description = "Project Version", defaultValue = "1.0.0-SNAPSHOT")
    String version;

    @CommandLine.Option(names = {"-f", "--features"}, description = "Features (BPMN, DMN, DRL) for the project", split = ",", defaultValue = "DRL")
    List<Features> features;

    @CommandLine.Option(names = {"--runtime"}, description = "Target Runtime", defaultValue = "Quarkus")
    Runtime runtime;

    @Override
    public Integer call() throws Exception {

        if (!runtime.equals(Runtime.Quarkus)) {
            throw new RuntimeException();
        }

        CreateQuarkusProject createProject = new CreateQuarkusProject();
        Path pomPath = createProject.run(file, groupId, artifactId, version, features);

        AddToQuarkusProject addToQuarkusProject = new AddToQuarkusProject();
        addToQuarkusProject.run(pomPath.toFile(), componentType, features);

        return 0;
    }
}

@CommandLine.Command(name = "add-to-project", aliases = {"-a", "--add"})
class AddToProjectCommand implements Callable<Integer> {

    @CommandLine.Option(names = {"-p", "--path"}, description = "Path to the project folder or pom.xml file.")
    File file;

    @CommandLine.Option(names = {"-c", "--component"}, description = "BAMOE Component (PAM or DM)", defaultValue = "PAM")
    ComponentType componentType;

    @CommandLine.Option(names = {"-f", "--features"}, description = "Features (BPMN, DMN, DRL) for the project", split = ",", defaultValue = "DRL")
    List<Features> features;

    @CommandLine.Option(names = {"--runtime"}, description = "Target Runtime", defaultValue = "Quarkus")
    Runtime runtime;

    @Override
    public Integer call() throws Exception {

        if (!runtime.equals(Runtime.Quarkus)) {
            throw new RuntimeException();
        }

        AddToQuarkusProject addToQuarkusProject = new AddToQuarkusProject();
        addToQuarkusProject.run(file, componentType, features);
        return 0;
    }
}
