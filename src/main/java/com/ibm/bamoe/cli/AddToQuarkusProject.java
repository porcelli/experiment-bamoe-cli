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
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

import picocli.CommandLine.Help.Ansi;

public class AddToQuarkusProject {

    private static final Dependency BAMOE_BOM = new Dependency(){{
        setGroupId("${kogito.bom.group-id}");
        setArtifactId("${kogito.bom.artifact-id}");
        setVersion("${kogito.bom.version}");
        setType("pom");
        setScope("import");
    }};

    private static final Properties BAMOE_PROPERTIES = new Properties(){{
        setProperty("kogito.bom.group-id", "com.ibm.bamoe");
        setProperty("kogito.bom.artifact-id", "bamoe-bom");
        setProperty("kogito.bom.version", "9.0.0.Final");
    }};

    public void run(File inputPath, ComponentType componentType, List<Features> features) {
        File pomFile;

        if (!Files.exists(inputPath.toPath())){
            System.out.println(Ansi.AUTO.string("@|bold,red POM File or project folder doesn't exists.|@"));
            return;
        }

        if (Files.isDirectory(inputPath.toPath())) {
            pomFile = new File(inputPath, "pom.xml");
        } else {
            pomFile = inputPath;
        }

        if (!Files.exists(pomFile.toPath())){
            System.out.println(Ansi.AUTO.string("@|bold,red POM File doesn't exists.|@"));
            return;
        }

        System.out.println(Ansi.AUTO.string("@|bold,yellow Make sure this is a Quarkus 2 based project.|@"));

        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model;

        try {
            model = reader.read(new FileReader(pomFile));

            model.getProperties().putAll(BAMOE_PROPERTIES);

            // Get DependencyManagement section
            DependencyManagement dependencyManagement = model.getDependencyManagement();
            if (dependencyManagement == null) {
                dependencyManagement = new DependencyManagement();
                model.setDependencyManagement(dependencyManagement);
            }
            List<Dependency> dependencyManagementDependencies = dependencyManagement.getDependencies();

            // Check if the dependency already exists in DependencyManagement section
            if (!dependencyManagementDependencies.contains(BAMOE_BOM)) {
                dependencyManagementDependencies.add(BAMOE_BOM);
            }

            final Set<Dependency> hashSet = new HashSet<>();
            hashSet.add(componentType.getDepedency());
            for (Features feature : features) {
                hashSet.addAll( feature.getDepedencies());
                hashSet.addAll( feature.getQuarkusDepedencies());
            }

            for (Dependency dependency : hashSet) {
                if (!model.getDependencies().contains(dependency)) {
                    model.getDependencies().add(dependency);
                }
            }

            // Write changes back to POM file
            MavenXpp3Writer writer = new MavenXpp3Writer();
            writer.write(new FileWriter(pomFile), model);

        System.out.println(Ansi.AUTO.string("@|bold,green POM File changed successuflly.|@"));
        } catch (Exception ex) {
            System.out.println(Ansi.AUTO.string("@|bold,red An error happened during POM file change.|@"));
        }
    }
}