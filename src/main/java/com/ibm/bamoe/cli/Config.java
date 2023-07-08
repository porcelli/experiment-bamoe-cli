package com.ibm.bamoe.cli;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.maven.model.Dependency;

// * BPMN, DMN, DRL
// BPMN, DMN
// BPMN, DRL
// DMN, DRL
// BPMN
// * DMN
// * DRL
public class Config {

    final Map<Runtime, RuntimeConfig> runtimes = new HashMap<>();
    final Map<String, FeatureConfig> features = new HashMap<>();

    public Config() {
    }

    public Map<Runtime, RuntimeConfig> getRuntimes(){
        return runtimes;
    }

    public Map<String, FeatureConfig> getFeatures(){
        return features;
    }

    public static class RuntimeConfig {
        private final Set<Dependency> dependencies = new HashSet<>();
        private final Set<String> examples = new HashSet<>();

        public RuntimeConfig() {
        }

        public Set<Dependency> getDependencies() {
            return new HashSet<>(dependencies);
        }

        public Set<String> getExamples() {
            return new HashSet<>(examples);
        }

        public void setDependencies(Set<Dependency> dependencies) {
            this.dependencies.clear();
            this.dependencies.addAll(dependencies);
        }

        public void addDependencies(Collection<Dependency> dependencies) {
            this.dependencies.addAll(dependencies);
        }

        public void setExamples(Set<Dependency> dependencies) {
            this.dependencies.clear();
            this.dependencies.addAll(dependencies);
        }

        public void addExamples(Collection<String> examples) {
            this.examples.addAll(examples);
        }
    }

    public static class FeatureConfig {
        private final Set<Dependency> dependencies = new HashSet<>();
        private final Set<String> examples = new HashSet<>();
        private final Map<String, RuntimeConfig> runtimes = new HashMap<>();

        public FeatureConfig() {
        }

        public Set<String> getExamples(){
            return examples;
        }

        public void addExamples(Collection<String> examples){
            this.examples.addAll(examples);
        }

        public void setExamples(Set<String> examples){
            this.examples.clear();
            this.examples.addAll(examples);
        }

        public void setDependencies(Set<Dependency> dependencies) {
            this.dependencies.addAll(dependencies);
        }

        public Set<Dependency> getDependencies() {
            return dependencies;
        }

        public Map<String, RuntimeConfig> getRuntimes() {
            return runtimes;
        }

        public void setRuntimes(Map<String, RuntimeConfig> runtimeConfig ) {
            this.runtimes.clear();
            this.runtimes.putAll(runtimeConfig);
        }

        public void addRuntime(String runtime, RuntimeConfig config) {
            this.runtimes.put(runtime, config);
        }
    }
}