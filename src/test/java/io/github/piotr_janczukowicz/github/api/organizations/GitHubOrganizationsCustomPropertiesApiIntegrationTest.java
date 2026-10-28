package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationsCustomPropertiesApiIntegrationTest {

    private static final JsonType<List<GitHubCustomProperty>> CUSTOM_PROPERTIES_TYPE =
            JsonTypes.listOf(GitHubCustomProperty.class);
    private static final JsonType<List<GitHubOrgRepoCustomPropertyValues>> ORG_REPO_VALUES_TYPE =
            JsonTypes.listOf(GitHubOrgRepoCustomPropertyValues.class);
    private static final JsonType<List<GitHubExternalProperty>> EXTERNAL_PROPERTIES_TYPE =
            JsonTypes.listOf(GitHubExternalProperty.class);
    private static final JsonType<List<GitHubOrganizationExternalPropertyInstallation>> EXTERNAL_INSTALLATIONS_TYPE =
            JsonTypes.listOf(GitHubOrganizationExternalPropertyInstallation.class);

    private static final String ORG = "github";
    private static final String PROPERTY_NAME = "environment";
    private static final String SCHEMA_PATH = "/orgs/github/properties/schema";
    private static final String DEFINITION_PATH = "/orgs/github/properties/schema/environment";
    private static final String MISSING_DEFINITION_PATH = "/orgs/github/properties/schema/missing";
    private static final String VALUES_PATH = "/orgs/github/properties/values";
    private static final String INSTALLATIONS_PATH = "/orgs/github/properties/installations";
    private static final String INSTALLATIONS_SCHEMA_PATH = "/orgs/github/properties/installations/schema";
    private static final String INSTALLATIONS_VALUES_PATH = "/orgs/github/properties/installations/values";
    private static final String INSTALLATIONS_VALUES_PROPERTY_PATH =
            "/orgs/github/properties/installations/values/environment";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listDefinitions_shouldReturnDefinitions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                SCHEMA_PATH,
                okJson(jsonBytes(
                        GitHubOrganizationsCustomPropertiesApiIntegrationTest.class, "custom-properties.json")));

        final List<GitHubCustomProperty> definitions;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            definitions = organizations.api().customProperties().listDefinitions(ORG);
        }

        final List<GitHubCustomProperty> expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsCustomPropertiesApiIntegrationTest.class, "custom-properties.json"),
                CUSTOM_PROPERTIES_TYPE);

        assertThat(definitions).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, SCHEMA_PATH);
    }

    @Test
    void createOrUpdateDefinitions_shouldReturnDefinitions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                SCHEMA_PATH,
                okJson(jsonBytes(
                        GitHubOrganizationsCustomPropertiesApiIntegrationTest.class, "custom-properties.json")));
        final GitHubCreateOrUpdateOrganizationDefinitionsPayload payload =
                OrganizationsCatalogFixtures.createOrUpdateOrganizationDefinitionsPayload();

        final List<GitHubCustomProperty> definitions;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            definitions = organizations.api().customProperties().createOrUpdateDefinitions(ORG, payload);
        }

        assertThat(definitions).hasSize(3);
        HTTP.verifyRequested(PATCH, SCHEMA_PATH);
        HTTP.verifyHeader(PATCH, SCHEMA_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, SCHEMA_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getDefinition_shouldReturnDefinition_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                DEFINITION_PATH,
                okJson(jsonBytes(GitHubOrganizationsCustomPropertiesApiIntegrationTest.class, "custom-property.json")));

        final Optional<GitHubCustomProperty> definition;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            definition = organizations.api().customProperties().getDefinition(ORG, PROPERTY_NAME);
        }

        final GitHubCustomProperty expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsCustomPropertiesApiIntegrationTest.class, "custom-property.json"),
                GitHubCustomProperty.class);

        assertThat(definition).contains(expected);
        HTTP.verifyRequested(GET, DEFINITION_PATH);
    }

    @Test
    void getDefinition_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, MISSING_DEFINITION_PATH, notFound());

        final Optional<GitHubCustomProperty> definition;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            definition = organizations.api().customProperties().getDefinition(ORG, "missing");
        }

        assertThat(definition).isEmpty();
        HTTP.verifyRequested(GET, MISSING_DEFINITION_PATH);
    }

    @Test
    void createOrUpdateDefinition_shouldReturnDefinition_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                DEFINITION_PATH,
                okJson(jsonBytes(GitHubOrganizationsCustomPropertiesApiIntegrationTest.class, "custom-property.json")));
        final GitHubCustomPropertySetPayload payload = OrganizationsCatalogFixtures.customPropertySetPayload();

        final GitHubCustomProperty definition;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            definition = organizations.api().customProperties().createOrUpdateDefinition(ORG, PROPERTY_NAME, payload);
        }

        final GitHubCustomProperty expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsCustomPropertiesApiIntegrationTest.class, "custom-property.json"),
                GitHubCustomProperty.class);

        assertThat(definition).isEqualTo(expected);
        HTTP.verifyRequested(PUT, DEFINITION_PATH);
        HTTP.verifyHeader(PUT, DEFINITION_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, DEFINITION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteDefinition_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, DEFINITION_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().customProperties().deleteDefinition(ORG, PROPERTY_NAME);
        }

        HTTP.verifyRequested(DELETE, DEFINITION_PATH);
    }

    @Test
    void listValues_shouldReturnValues_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                VALUES_PATH,
                okJson(jsonBytes(
                        GitHubOrganizationsCustomPropertiesApiIntegrationTest.class,
                        "org-repo-custom-property-values.json")));

        final GitHubPage<GitHubOrgRepoCustomPropertyValues> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().customProperties().listValues(ORG);
        }

        final List<GitHubOrgRepoCustomPropertyValues> expected = CODEC.decode(
                jsonBytes(
                        GitHubOrganizationsCustomPropertiesApiIntegrationTest.class,
                        "org-repo-custom-property-values.json"),
                ORG_REPO_VALUES_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, VALUES_PATH);
    }

    @Test
    void listValues_shouldSendRepositoryQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                VALUES_PATH,
                okJson(jsonBytes(
                        GitHubOrganizationsCustomPropertiesApiIntegrationTest.class,
                        "org-repo-custom-property-values.json")));
        final GitHubOrganizationsCustomPropertyValuesFilter filter =
                GitHubOrganizationsCustomPropertyValuesFilterBuilder.builder()
                        .repositoryQuery("Hello-World")
                        .build();

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().customProperties().listValues(ORG, filter);
        }

        HTTP.verifyRequested(GET, VALUES_PATH);
        HTTP.verifyQueryParam(GET, VALUES_PATH, "repository_query", "Hello-World");
    }

    @Test
    void createOrUpdateValues_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PATCH, VALUES_PATH, noContent());
        final GitHubCreateOrUpdateOrganizationValuesPayload payload =
                OrganizationsCatalogFixtures.createOrUpdateOrganizationValuesPayload();

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().customProperties().createOrUpdateValues(ORG, payload);
        }

        HTTP.verifyRequested(PATCH, VALUES_PATH);
        HTTP.verifyHeader(PATCH, VALUES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, VALUES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void listExternalAppInstallations_shouldReturnInstallations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                INSTALLATIONS_PATH,
                okJson(jsonBytes(
                        GitHubOrganizationsCustomPropertiesApiIntegrationTest.class,
                        "organization-external-property-installations.json")));

        final List<GitHubOrganizationExternalPropertyInstallation> installations;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            installations = organizations.api().customProperties().listExternalAppInstallations(ORG);
        }

        final List<GitHubOrganizationExternalPropertyInstallation> expected = CODEC.decode(
                jsonBytes(
                        GitHubOrganizationsCustomPropertiesApiIntegrationTest.class,
                        "organization-external-property-installations.json"),
                EXTERNAL_INSTALLATIONS_TYPE);

        assertThat(installations).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, INSTALLATIONS_PATH);
    }

    @Test
    void registerExternalAppInstallation_shouldReturnInstallation_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                INSTALLATIONS_PATH,
                createdJson(jsonBytes(
                        GitHubOrganizationsCustomPropertiesApiIntegrationTest.class,
                        "organization-external-property-installation.json")));
        final GitHubExternalPropertiesRegisterInstallationPayload payload =
                OrganizationsCatalogFixtures.externalPropertiesRegisterInstallationPayload();

        final GitHubOrganizationExternalPropertyInstallation installation;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            installation = organizations.api().customProperties().registerExternalAppInstallation(ORG, payload);
        }

        final GitHubOrganizationExternalPropertyInstallation expected = CODEC.decode(
                jsonBytes(
                        GitHubOrganizationsCustomPropertiesApiIntegrationTest.class,
                        "organization-external-property-installation.json"),
                GitHubOrganizationExternalPropertyInstallation.class);

        assertThat(installation).isEqualTo(expected);
        HTTP.verifyRequested(POST, INSTALLATIONS_PATH);
        HTTP.verifyHeader(POST, INSTALLATIONS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, INSTALLATIONS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void listExternalDefinitions_shouldReturnDefinitions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                INSTALLATIONS_SCHEMA_PATH,
                okJson(jsonBytes(
                        GitHubOrganizationsCustomPropertiesApiIntegrationTest.class, "external-properties.json")));

        final List<GitHubExternalProperty> definitions;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            definitions = organizations.api().customProperties().listExternalDefinitions(ORG);
        }

        final List<GitHubExternalProperty> expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsCustomPropertiesApiIntegrationTest.class, "external-properties.json"),
                EXTERNAL_PROPERTIES_TYPE);

        assertThat(definitions).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, INSTALLATIONS_SCHEMA_PATH);
    }

    @Test
    void createOrUpdateExternalValues_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PATCH, INSTALLATIONS_VALUES_PATH, noContent());
        final GitHubExternalPropertiesCreateOrUpdateValuesPayload payload =
                OrganizationsCatalogFixtures.externalPropertiesCreateOrUpdateValuesPayload();

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().customProperties().createOrUpdateExternalValues(ORG, payload);
        }

        HTTP.verifyRequested(PATCH, INSTALLATIONS_VALUES_PATH);
        HTTP.verifyHeader(PATCH, INSTALLATIONS_VALUES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, INSTALLATIONS_VALUES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void createOrUpdateExternalValuesForProperty_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PATCH, INSTALLATIONS_VALUES_PROPERTY_PATH, noContent());
        final GitHubExternalPropertiesCreateOrUpdatePropertyValuesPayload payload =
                OrganizationsCatalogFixtures.externalPropertiesCreateOrUpdatePropertyValuesPayload();

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().customProperties().createOrUpdateExternalValuesForProperty(ORG, PROPERTY_NAME, payload);
        }

        HTTP.verifyRequested(PATCH, INSTALLATIONS_VALUES_PROPERTY_PATH);
        HTTP.verifyHeader(PATCH, INSTALLATIONS_VALUES_PROPERTY_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(
                PATCH, INSTALLATIONS_VALUES_PROPERTY_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteExternalValues_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, INSTALLATIONS_VALUES_PROPERTY_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().customProperties().deleteExternalValues(ORG, PROPERTY_NAME);
        }

        HTTP.verifyRequested(DELETE, INSTALLATIONS_VALUES_PROPERTY_PATH);
    }
}
