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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

import picocli.CommandLine.Help.Ansi;

public class CreateQuarkusProject {

    public Path run(File file, String groupId, String artifactId, String version, List<Features> features) {
        if (!Files.exists(file.toPath())) {
            System.out.println(Ansi.AUTO.string("@|bold,red Path doesn't exists.|@"));
            return null;
        }

        if (!Files.isDirectory(file.toPath())) {
            System.out.println(Ansi.AUTO.string("@|bold,red Path is not a folder.|@"));
            return null;
        }

        String projectDir = file.getAbsolutePath();

        new File(projectDir + "/" + artifactId + "/src/main/java").mkdirs();
        new File(projectDir + "/" + artifactId + "/src/main/resources").mkdirs();
        new File(projectDir + "/" + artifactId + "/src/test/java").mkdirs();
        new File(projectDir + "/" + artifactId + "/src/test/resources").mkdirs();
        Path pomPath = Paths.get(projectDir + "/" + artifactId + "/pom.xml");

        try (InputStream inputStream = getClass().getResourceAsStream("/pom-template.xml");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append('\n');
            }

            Files.write(pomPath, content.toString().getBytes());
        } catch (IOException e) {
        }

        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model;

        try {
            model = reader.read(new FileReader(pomPath.toFile()));
            model.setGroupId(groupId);
            model.setVersion(version);
            model.setArtifactId(artifactId);
            MavenXpp3Writer writer = new MavenXpp3Writer();
            writer.write(new FileWriter(pomPath.toFile()), model);
        } catch (Exception ex) {
        }

        return pomPath;
    }
}