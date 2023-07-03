package com.ibm.bamoe.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum FilesToWriteQuarkus {

    ALL("/quarkus/all/src/main/java/org/kie/kogito/traffic/licensevalidated/Validated.java",
            "/quarkus/all/src/main/java/org/kie/kogito/traffic/licensevalidated/LicenseValidatedService.java",
            "/quarkus/all/src/main/java/org/kie/kogito/traffic/licensevalidation/LicenseValidationService.java",
            "/quarkus/all/src/main/java/org/kie/kogito/traffic/licensevalidation/Driver.java",
            "/quarkus/all/src/main/java/org/kie/kogito/traffic/Violation.java",
            "/quarkus/all/src/main/java/org/kie/kogito/traffic/DriverService.java",
            "/quarkus/all/src/main/java/org/kie/kogito/traffic/Fine.java",
            "/quarkus/all/src/main/resources/traffic-rules-dmn.bpmn",
            "/quarkus/all/src/main/resources/LicenseValidatedService.drl",
            "/quarkus/all/src/main/resources/TrafficViolation.dmn",
            "/quarkus/all/src/main/resources/LicenseValidationService.drl",
            "/quarkus/all/src/main/resources/application.properties",
            "/quarkus/all/src/test/java/org/kie/kogito/traffic/TrafficProcessIT.java"),
    DRL("/quarkus/drl/src/main/java/org/kie/kogito/queries/Applicant.java",
            "/quarkus/drl/src/main/java/org/kie/kogito/queries/LoanApplication.java",
            "/quarkus/drl/src/main/java/org/kie/kogito/queries/AllAmounts.java",
            "/quarkus/drl/src/main/java/org/kie/kogito/queries/LoanUnit.java",
            "/quarkus/drl/src/main/resources/org/kie/kogito/queries/RuleUnitQuery.drl",
            "/quarkus/drl/src/test/java/org/kie/kogito/decisiontable/quarkus/ruleunit/RestQueryTest.java",
            "/quarkus/drl/src/test/resources/application.properties"),
    DMN("/quarkus/dmn/src/main/resources/Traffic Violation.dmn",
            "/quarkus/dmn/src/main/resources/application.properties",
            "/quarkus/dmn/src/test/resources/TrafficViolationTest.scesim",
            "/quarkus/dmn/src/test/resources/application.properties",
            "/quarkus/dmn/src/test/java/testscenario/KogitoScenarioJunitActivatorTest.java",
            "/quarkus/dmn/src/test/java/org/kie/kogito/dmn/quarkus/example/TrafficViolationTest.java");

    private final List<String> files = new ArrayList<>();

    FilesToWriteQuarkus(final String... files) {
        this.files.addAll(Arrays.asList(files));
    }

    List<String> getFiles() {
        return files;
    }
}
