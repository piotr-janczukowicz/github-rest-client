package io.github.piotr_janczukowicz.github.api.dependencygraph;

import java.time.Instant;
import java.util.List;
import java.util.Map;

final class DependencyGraphCatalogFixtures {

    private DependencyGraphCatalogFixtures() {}

    static GitHubDiffRangeFilter diffRangeFilter() {
        return GitHubDiffRangeFilterBuilder.builder().name("package.json").build();
    }

    static GitHubDependencyGraphDiffVulnerability dependencyGraphDiffVulnerability() {
        return GitHubDependencyGraphDiffVulnerabilityBuilder.builder()
                .severity("critical")
                .advisoryGhsaId("GHSA-rf4j-j272-fj86")
                .advisorySummary("A summary of the advisory.")
                .advisoryUrl("https://github.com/advisories/GHSA-rf4j-j272-fj86")
                .build();
    }

    static GitHubDependencyGraphDiffEntry dependencyGraphDiffEntry() {
        return GitHubDependencyGraphDiffEntryBuilder.builder()
                .changeType(GitHubDependencyGraphDiffChangeType.ADDED)
                .manifest("path/to/package-lock.json")
                .ecosystem("npm")
                .name("@actions/core")
                .version("1.0.0")
                .packageUrl("pkg:/npm/%40actions/core@1.1.0")
                .license("MIT")
                .sourceRepositoryUrl("https://github.com/github/actions")
                .vulnerabilities(List.of(dependencyGraphDiffVulnerability()))
                .scope(GitHubDependencyGraphDiffScope.RUNTIME)
                .build();
    }

    static GitHubGenerateSbomReportResponse generateSbomReportResponse() {
        return GitHubGenerateSbomReportResponseBuilder.builder()
                .sbomUrl("https://api.github.com/repos/octocat/Hello-World/dependency-graph/sbom/fetch-report/abc")
                .build();
    }

    static GitHubCreateRepositorySnapshotResponse createRepositorySnapshotResponse() {
        return GitHubCreateRepositorySnapshotResponseBuilder.builder()
                .id(12_345)
                .createdAt("2018-05-04T01:14:52Z")
                .result("SUCCESS")
                .message("Dependency results for the repo have been successfully updated.")
                .build();
    }

    static GitHubSnapshotJob snapshotJob() {
        return GitHubSnapshotJobBuilder.builder()
                .id("5622a2b0-63f6-4732-8c34-a1ab27e102a11")
                .correlator("yourworkflowname_yourjobname")
                .htmlUrl("http://example.com/build")
                .build();
    }

    static GitHubSnapshotDetector snapshotDetector() {
        return GitHubSnapshotDetectorBuilder.builder()
                .name("docker buildtools")
                .version("1.0.0")
                .url("http://example.com/docker-buildtools")
                .build();
    }

    static GitHubManifestFile manifestFile() {
        return GitHubManifestFileBuilder.builder()
                .sourceLocation("src/build.package-lock.json")
                .build();
    }

    static GitHubDependency dependency() {
        return dependency("value");
    }

    static GitHubDependency dependency(final String metadataValue) {
        return GitHubDependencyBuilder.builder()
                .packageUrl("pkg:/maven/org.apache.commons/commons-lang3@3.12.0")
                .metadata(Map.of("example", metadataValue))
                .relationship(GitHubDependencyRelationship.DIRECT)
                .scope(GitHubDependencyScope.RUNTIME)
                .dependencies(List.of("pkg:/maven/commons-io/commons-io@2.11.0"))
                .build();
    }

    static GitHubManifest manifest() {
        return manifest("value");
    }

    static GitHubManifest manifest(final String metadataValue) {
        return GitHubManifestBuilder.builder()
                .name("package-lock.json")
                .file(manifestFile())
                .metadata(Map.of("example", metadataValue))
                .resolved(Map.of("@actions/core", dependency(metadataValue)))
                .build();
    }

    static GitHubCreateRepositorySnapshotPayload createRepositorySnapshotPayload() {
        return createRepositorySnapshotPayload("production", "value");
    }

    static GitHubCreateRepositorySnapshotPayload createRepositorySnapshotPayload(
            final String metadataValue, final String manifestMetadataValue) {
        return GitHubCreateRepositorySnapshotPayloadBuilder.builder()
                .version(0)
                .job(snapshotJob())
                .sha("ce587453ced02b1526dfb4cb910479d431683101")
                .ref("refs/heads/main")
                .detector(snapshotDetector())
                .metadata(Map.of("environment", metadataValue))
                .manifests(Map.of("package-lock.json", manifest(manifestMetadataValue)))
                .scanned(Instant.parse("2022-06-14T20:25:00Z"))
                .build();
    }

    static GitHubSpdxSbomExternalRef spdxSbomExternalRef() {
        return GitHubSpdxSbomExternalRefBuilder.builder()
                .referenceCategory("PACKAGE-MANAGER")
                .referenceLocator("pkg:maven/org.apache.commons/commons-lang3@3.12.0")
                .referenceType("purl")
                .build();
    }

    static GitHubSpdxSbomRelationship spdxSbomRelationship() {
        return GitHubSpdxSbomRelationshipBuilder.builder()
                .relationshipType("DEPENDS_ON")
                .spdxElementId("SPDXRef-DOCUMENT")
                .relatedSpdxElement("SPDXRef-Package-1")
                .build();
    }

    static GitHubSpdxSbomCreationInfo spdxSbomCreationInfo() {
        return GitHubSpdxSbomCreationInfoBuilder.builder()
                .created("2021-11-03T00:00:00Z")
                .creators(List.of("GitHub.com-Dependency-Graph"))
                .build();
    }

    static GitHubSpdxSbomPackage spdxSbomPackage() {
        return GitHubSpdxSbomPackageBuilder.builder()
                .spdxId("SPDXRef-Package-1")
                .name("commons-lang3")
                .versionInfo("3.12.0")
                .downloadLocation("NOASSERTION")
                .filesAnalyzed(false)
                .licenseConcluded("Apache-2.0")
                .licenseDeclared("Apache-2.0")
                .supplier("NOASSERTION")
                .copyrightText("Copyright Apache")
                .externalRefs(List.of(spdxSbomExternalRef()))
                .build();
    }

    static GitHubSpdxSbom spdxSbom() {
        return GitHubSpdxSbomBuilder.builder()
                .spdxId("SPDXRef-DOCUMENT")
                .spdxVersion("SPDX-2.3")
                .comment("Generated by GitHub")
                .creationInfo(spdxSbomCreationInfo())
                .name("com.github/octo-org/octo-repo")
                .dataLicense("CC0-1.0")
                .documentNamespace("https://github.com/octo-org/octo-repo/dependency_graph/sbom-123")
                .packages(List.of(spdxSbomPackage()))
                .relationships(List.of(spdxSbomRelationship()))
                .build();
    }

    static GitHubDependencyGraphSpdxSbom dependencyGraphSpdxSbom() {
        return GitHubDependencyGraphSpdxSbomBuilder.builder().sbom(spdxSbom()).build();
    }

    public static GitHubEnterpriseLicensePolicyItemLicense enterpriseLicensePolicyItemLicense() {
        return new GitHubEnterpriseLicensePolicyItemLicense("MIT", Instant.parse("2026-01-01T00:00:00Z"));
    }

    public static GitHubEnterpriseLicensePolicyItemLicenses enterpriseLicensePolicyItemLicenses() {
        return new GitHubEnterpriseLicensePolicyItemLicenses(List.of(enterpriseLicensePolicyItemLicense()));
    }

    public static GitHubEnterpriseLicensePolicyItemPackage enterpriseLicensePolicyItemPackage() {
        return new GitHubEnterpriseLicensePolicyItemPackage(
                GitHubEnterpriseLicensePolicyItemPackageManager.NPM,
                "left-pad",
                GitHubEnterpriseLicensePolicyItemPackageAction.ALLOWED,
                "Approved by the security team.",
                List.of("Apache-2.0"),
                Instant.parse("2026-01-02T00:00:00Z"));
    }

    public static GitHubEnterpriseLicensePolicyItem enterpriseLicensePolicyItem() {
        return new GitHubEnterpriseLicensePolicyItem(
                enterpriseLicensePolicyItemLicenses(), List.of(enterpriseLicensePolicyItemPackage()));
    }

    public static GitHubEnterpriseLicensePolicyResponse enterpriseLicensePolicyResponse() {
        return new GitHubEnterpriseLicensePolicyResponse(
                Instant.parse("2026-01-01T00:00:00Z"),
                enterpriseLicensePolicyItemLicenses(),
                List.of(enterpriseLicensePolicyItemPackage()));
    }

    public static GitHubCheckLicenseComplianceForPackagesPayload checkLicenseComplianceForPackagesPayload() {
        return new GitHubCheckLicenseComplianceForPackagesPayload(List.of("pkg:npm/left-pad@1.3.0"));
    }

    public static GitHubCheckLicenseComplianceForPackagesPurlResult checkLicenseComplianceForPackagesPurlResult() {
        return new GitHubCheckLicenseComplianceForPackagesPurlResult(
                GitHubCheckLicenseComplianceForPackagesPurlResultStatus.ALLOWED,
                "MIT",
                GitHubCheckLicenseComplianceForPackagesPurlResultReason.ALLOWED_LICENSE);
    }

    public static GitHubCheckLicenseComplianceForPackagesResponse checkLicenseComplianceForPackagesResponse() {
        return new GitHubCheckLicenseComplianceForPackagesResponse(
                Map.of("pkg:npm/left-pad@1.3.0", checkLicenseComplianceForPackagesPurlResult()));
    }
}
