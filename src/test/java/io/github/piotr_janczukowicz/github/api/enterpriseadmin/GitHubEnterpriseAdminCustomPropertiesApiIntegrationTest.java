package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminCustomPropertiesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubCustomProperty;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminCustomPropertiesApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminCustomPropertiesApiIntegrationTest.class;
    private static final String DEFINITIONS_PATH = "/enterprises/octo-enterprise/properties/schema";
    private static final String DEFINITION_PATH = DEFINITIONS_PATH + "/environment";
    private static final String PROMOTE_PATH = DEFINITIONS_PATH + "/organizations/octo-org/environment/promote";
    private static final String LIST_JSON = "custom-property-list.json";
    private static final String PROPERTY_JSON = "custom-property.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getEnterpriseDefinitions_shouldReturnDefinitions_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, DEFINITIONS_PATH, okJson(jsonBytes(RESOURCES, LIST_JSON)));

        final List<GitHubCustomProperty> definitions;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesApiClient api =
                enterpriseAdminCustomPropertiesApi(HTTP)) {
            definitions = api.api().getEnterpriseDefinitions(ENTERPRISE);
        }

        assertThat(definitions).containsExactly(OrganizationsCatalogFixtures.customProperty());
        HTTP.verifyRequested(GET, DEFINITIONS_PATH);
    }

    @Test
    void createOrUpdateEnterpriseDefinitions_shouldReturnDefinitions_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, DEFINITIONS_PATH, okJson(jsonBytes(RESOURCES, LIST_JSON)));

        final List<GitHubCustomProperty> definitions;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesApiClient api =
                enterpriseAdminCustomPropertiesApi(HTTP)) {
            definitions = api.api()
                    .createOrUpdateEnterpriseDefinitions(
                            ENTERPRISE,
                            EnterpriseAdminCatalogFixtures
                                    .customPropertiesCreateOrUpdateEnterpriseDefinitionsPayload());
        }

        assertThat(definitions).containsExactly(OrganizationsCatalogFixtures.customProperty());
        HTTP.verifyRequested(PATCH, DEFINITIONS_PATH);
    }

    @Test
    void promoteDefinitionToEnterprise_shouldReturnDefinition_whenResponseIsOk() throws Exception {
        HTTP.stub(PUT, PROMOTE_PATH, okJson(jsonBytes(RESOURCES, PROPERTY_JSON)));

        final GitHubCustomProperty definition;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesApiClient api =
                enterpriseAdminCustomPropertiesApi(HTTP)) {
            definition = api.api().promoteDefinitionToEnterprise(ENTERPRISE, "octo-org", "environment");
        }

        assertThat(definition).isEqualTo(OrganizationsCatalogFixtures.customProperty());
        HTTP.verifyRequested(PUT, PROMOTE_PATH);
    }

    @Test
    void getEnterpriseDefinition_shouldReturnDefinition_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, DEFINITION_PATH, okJson(jsonBytes(RESOURCES, PROPERTY_JSON)));

        final Optional<GitHubCustomProperty> definition;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesApiClient api =
                enterpriseAdminCustomPropertiesApi(HTTP)) {
            definition = api.api().getEnterpriseDefinition(ENTERPRISE, "environment");
        }

        assertThat(definition).contains(OrganizationsCatalogFixtures.customProperty());
        HTTP.verifyRequested(GET, DEFINITION_PATH);
    }

    @Test
    void getEnterpriseDefinition_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, DEFINITION_PATH, notFound());

        final Optional<GitHubCustomProperty> definition;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesApiClient api =
                enterpriseAdminCustomPropertiesApi(HTTP)) {
            definition = api.api().getEnterpriseDefinition(ENTERPRISE, "environment");
        }

        assertThat(definition).isEmpty();
    }

    @Test
    void createOrUpdateEnterpriseDefinition_shouldReturnDefinition_whenResponseIsOk() throws Exception {
        HTTP.stub(PUT, DEFINITION_PATH, okJson(jsonBytes(RESOURCES, PROPERTY_JSON)));

        final GitHubCustomProperty definition;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesApiClient api =
                enterpriseAdminCustomPropertiesApi(HTTP)) {
            definition = api.api()
                    .createOrUpdateEnterpriseDefinition(
                            ENTERPRISE, "environment", OrganizationsCatalogFixtures.customPropertySetPayload());
        }

        assertThat(definition).isEqualTo(OrganizationsCatalogFixtures.customProperty());
        HTTP.verifyRequested(PUT, DEFINITION_PATH);
    }

    @Test
    void deleteEnterpriseDefinition_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, DEFINITION_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesApiClient api =
                enterpriseAdminCustomPropertiesApi(HTTP)) {
            api.api().deleteEnterpriseDefinition(ENTERPRISE, "environment");
        }

        HTTP.verifyRequested(DELETE, DEFINITION_PATH);
    }
}
