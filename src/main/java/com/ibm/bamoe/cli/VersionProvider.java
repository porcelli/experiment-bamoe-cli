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

import java.io.InputStream;
import java.util.Properties;

import picocli.CommandLine.IVersionProvider;

public class VersionProvider implements IVersionProvider {

    public String[] getVersion() {
        Properties properties = new Properties();
        try (InputStream inputStream = VersionProvider.class.getResourceAsStream("/git.properties")) {
            properties.load(inputStream);
        } catch (Exception e) {
            properties.setProperty("git.commit.id", "--none--");
        }

        return new String[] { 
                "@|blue Copyright 2023 IBM Corp.|@",
                "@|blue IBM Business Automation Manager Open Editions (BAMOE) CLI 9.x |@",
                "@|blue build " + properties.getProperty("git.commit.id").substring(0, 8) + "|@" };
    }
}
