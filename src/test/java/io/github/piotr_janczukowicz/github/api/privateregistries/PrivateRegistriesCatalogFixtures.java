package io.github.piotr_janczukowicz.github.api.privateregistries;

import io.github.piotr_janczukowicz.json.OptionalNull;
import java.net.URI;
import java.time.Instant;
import java.util.List;

final class PrivateRegistriesCatalogFixtures {

    private static final Instant CREATED_AT = Instant.parse("2024-01-01T00:00:00Z");
    private static final Instant UPDATED_AT = Instant.parse("2024-01-01T00:00:00Z");
    private static final String URL = "https://maven.pkg.github.com/organization/";
    private static final String TENANT_ID = "72f988bf-86f1-41af-91ab-2d7cd011db47";
    private static final String CLIENT_ID = "8b5c3f9e-1d2a-4c6b-9e7f-0a1b2c3d4e5f";
    private static final String AWS_REGION = "us-east-1";
    private static final String ACCOUNT_ID = "123456789012";
    private static final String ROLE_NAME = "dependabot-role";
    private static final String DOMAIN = "my-domain";
    private static final String DOMAIN_OWNER = "210987654321";
    private static final String JFROG_OIDC_PROVIDER_NAME = "github-oidc";
    private static final String AUDIENCE = "sts.amazonaws.com";
    private static final String IDENTITY_MAPPING_NAME = "dependabot-mapping";
    private static final String NAMESPACE = "my-org";
    private static final String SERVICE_SLUG = "dependabot-sa";
    private static final String API_HOST = "api.cloudsmith.io";
    private static final String WORKLOAD_IDENTITY_PROVIDER =
            "projects/123456/locations/global/workloadIdentityPools/github/providers/dependabot";
    private static final String SERVICE_ACCOUNT = "dependabot@my-project.iam.gserviceaccount.com";

    private PrivateRegistriesCatalogFixtures() {}

    static GitHubOrgPrivateRegistryConfiguration orgPrivateRegistryConfiguration() {
        return new GitHubOrgPrivateRegistryConfiguration(
                "MAVEN_REPOSITORY_SECRET",
                GitHubPrivateRegistryType.MAVEN_REPOSITORY,
                GitHubPrivateRegistryAuthType.TOKEN,
                URL,
                "monalisa",
                true,
                GitHubPrivateRegistryVisibility.PRIVATE,
                TENANT_ID,
                CLIENT_ID,
                AWS_REGION,
                ACCOUNT_ID,
                ROLE_NAME,
                DOMAIN,
                DOMAIN_OWNER,
                JFROG_OIDC_PROVIDER_NAME,
                AUDIENCE,
                IDENTITY_MAPPING_NAME,
                NAMESPACE,
                SERVICE_SLUG,
                API_HOST,
                WORKLOAD_IDENTITY_PROVIDER,
                SERVICE_ACCOUNT,
                CREATED_AT,
                UPDATED_AT);
    }

    static GitHubOrgPrivateRegistryConfigurationWithSelectedRepositories
            orgPrivateRegistryConfigurationWithSelectedRepositories() {
        return new GitHubOrgPrivateRegistryConfigurationWithSelectedRepositories(
                "MAVEN_REPOSITORY_SECRET",
                GitHubPrivateRegistryType.MAVEN_REPOSITORY,
                GitHubPrivateRegistryAuthType.TOKEN,
                URL,
                "monalisa",
                true,
                GitHubPrivateRegistryVisibility.SELECTED,
                List.of(1_296_269L, 1_296_280L),
                TENANT_ID,
                CLIENT_ID,
                AWS_REGION,
                ACCOUNT_ID,
                ROLE_NAME,
                DOMAIN,
                DOMAIN_OWNER,
                JFROG_OIDC_PROVIDER_NAME,
                AUDIENCE,
                IDENTITY_MAPPING_NAME,
                NAMESPACE,
                SERVICE_SLUG,
                API_HOST,
                WORKLOAD_IDENTITY_PROVIDER,
                SERVICE_ACCOUNT,
                CREATED_AT,
                UPDATED_AT);
    }

    static GitHubCreateOrgPrivateRegistryPayload createOrgPrivateRegistryPayload() {
        return GitHubCreateOrgPrivateRegistryPayloadBuilder.builder()
                .registryType(GitHubPrivateRegistryType.MAVEN_REPOSITORY)
                .url(URI.create("https://maven.pkg.github.com/organization/"))
                .visibility(GitHubPrivateRegistryVisibility.PRIVATE)
                .encryptedValue("c2VjcmV0")
                .build();
    }

    static GitHubUpdateOrgPrivateRegistryPayload updateOrgPrivateRegistryPayload() {
        return GitHubUpdateOrgPrivateRegistryPayloadBuilder.builder()
                .username(OptionalNull.of("monalisa"))
                .build();
    }

    static GitHubOrgPrivateRegistryConfigurationsPage orgPrivateRegistryConfigurationsPage() {
        return new GitHubOrgPrivateRegistryConfigurationsPage(1, List.of(orgPrivateRegistryConfiguration()));
    }

    static GitHubPrivateRegistriesPublicKey privateRegistriesPublicKey() {
        return new GitHubPrivateRegistriesPublicKey(
                "012345678912345678", "2Sg8iYjAxxmI2LvUXpJjkYrMxURPc8r+dB7TJyvv1234");
    }
}
