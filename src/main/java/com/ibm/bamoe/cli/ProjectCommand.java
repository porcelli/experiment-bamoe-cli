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

import static java.util.Objects.isNull;

import java.io.File;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.Callable;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Help.Ansi;
import picocli.CommandLine.Model.CommandSpec;

@TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = { CreateProjectCommand.class,
        AddToProjectCommand.class }, versionProvider = VersionProvider.class)
public class ProjectCommand {
}

enum Runtime {
    Quarkus// , SpringBoot, RHBQ
}

@CommandLine.Command(name = "create-project", aliases = { "--create" })
class CreateProjectCommand implements Callable<Integer> {

    @CommandLine.Option(names = { "-p",
            "--path" }, description = "Path to the folder where project will be created.", required = true)
    File path;

    @CommandLine.Option(names = { "-c",
            "--component" }, description = "BAMOE Component (PAM or DM)", defaultValue = "PAM")
    ComponentType componentType;

    @CommandLine.Option(names = { "-g", "--groupId" }, description = "Group ID of the project", required = true)
    String groupId;

    @CommandLine.Option(names = { "-a", "--artifactId" }, description = "Artifact ID of the project", required = true)
    String artifactId;

    @CommandLine.Option(names = { "-v", "--version" }, description = "Project Version", defaultValue = "1.0.0-SNAPSHOT")
    String version;

    @CommandLine.Option(names = { "-f",
            "--features" }, description = "Features (BPMN, DMN, DRL) for the project", split = ",", defaultValue = "DRL")
    Set<Features> features;

    @CommandLine.Option(names = { "-r", "--runtime" }, description = "Target Runtime", defaultValue = "Quarkus")
    Runtime runtime;

    @CommandLine.Option(names = { "-e", "--examples" }, description = "Include Code Examples?", defaultValue = "false")
    boolean includeExamples;

    @Spec
    CommandSpec spec;

    @Override
    public Integer call() throws Exception {

        if (!runtime.equals(Runtime.Quarkus)) {
            throw new ParameterException(spec.commandLine(), "Quarkus is the only supported runtime.");
        }

        if (isNull(path) || !path.exists() || !path.isDirectory()) {
            throw new ParameterException(spec.commandLine(),
                    "Path parameter invalid. Path must exists and be a directory.");
        }

        CreateQuarkusProject createProject = new CreateQuarkusProject();
        Path pomPath = createProject.run(path, groupId, artifactId, version, features);

        AddToQuarkusProject addToQuarkusProject = new AddToQuarkusProject();
        addToQuarkusProject.run(pomPath.toFile(), componentType, features, includeExamples ? CodeExamples.INCLUDE_EXAMPLES : CodeExamples.NO_EXAMPLES);

        System.out.println(Ansi.AUTO.string("@|bold,green BAMOE 9.x Quarkus Project created successuflly.|@"));

        return 0;
    }
}

@CommandLine.Command(name = "add-to-project", aliases = { "--add" })
class AddToProjectCommand implements Callable<Integer> {

    @CommandLine.Option(names = { "-p",
            "--path" }, description = "Path to the project folder or pom.xml file.", required = true)
    File path;

    @CommandLine.Option(names = { "-c",
            "--component" }, description = "BAMOE Component (PAM or DM)", defaultValue = "PAM")
    ComponentType componentType;

    @CommandLine.Option(names = { "-f",
            "--features" }, description = "Features (BPMN, DMN, DRL) for the project", split = ",", defaultValue = "DRL")
    Set<Features> features;

    @CommandLine.Option(names = { "-r", "--runtime" }, description = "Target Runtime", defaultValue = "Quarkus")
    Runtime runtime;

    @CommandLine.Option(names = { "-e", "--examples" }, description = "Include Code Examples?", defaultValue = "false")
    boolean includeExamples;

    @Spec
    CommandSpec spec;

    @Override
    public Integer call() throws Exception {

        if (!runtime.equals(Runtime.Quarkus)) {
            throw new ParameterException(spec.commandLine(), "Quarkus is the only supported runtime.");
        }

        if (isNull(path) || !path.exists()) {
            throw new ParameterException(spec.commandLine(),
                    "Path parameter invalid. Path must exists, it can point to project directory or to project pom.");
        }

        if (path.isDirectory() && new File(path, "pom.xml").exists()) {
            throw new ParameterException(spec.commandLine(),
                    "Path parameter invalid. Path is not a valid project directory, no pom.xml found.");
        }

        if (!path.getName().toLowerCase().equals("pom.xml")) {
            throw new ParameterException(spec.commandLine(),
                    "Path parameter invalid. Path is not a valid project directory nor a pom.xml file.");
        }

        System.out.println(Ansi.AUTO.string("@|bold,yellow Make sure this is a Quarkus 2 based project.|@"));

        AddToQuarkusProject addToQuarkusProject = new AddToQuarkusProject();
        addToQuarkusProject.run(path, componentType, features,includeExamples ? CodeExamples.INCLUDE_EXAMPLES : CodeExamples.NO_EXAMPLES);

        System.out.println(Ansi.AUTO.string("@|bold,green POM File changed successuflly.|@"));

        return 0;
    }
}
