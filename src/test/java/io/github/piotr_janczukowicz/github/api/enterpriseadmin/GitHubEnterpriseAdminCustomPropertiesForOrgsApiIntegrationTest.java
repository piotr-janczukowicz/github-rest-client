package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminCustomPropertiesForOrgsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationCustomProperty;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminCustomPropertiesForOrgsApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminCustomPropertiesForOrgsApiIntegrationTest.class;
    private static final String DEFINITIONS_PATH = "/enterprises/octo-enterprise/org-properties/schema";
    private static final String DEFINITION_PATH = DEFINITIONS_PATH + "/environment";
    private static final String VALUES_PATH = "/enterprises/octo-enterprise/org-properties/values";
    private static final String DEFINITION_JSON = "organization-custom-property.json";
    private static final String DEFINITIONS_JSON = "organization-custom-property-list.json";
    private static final String VALUES_JSON = "enterprise-org-property-values-list.json";
    private static final String CUSTOM_PROPERTY_NAME = "environment";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getEnterpriseDefinitions_shouldReturnDefinitions_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, DEFINITIONS_PATH, okJson(jsonBytes(RESOURCES, DEFINITIONS_JSON)));

        final List<GitHubOrganizationCustomProperty> definitions;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesForOrgsApiClient api =
                enterpriseAdminCustomPropertiesForOrgsApi(HTTP)) {
            definitions = api.api().getEnterpriseDefinitions(ENTERPRISE);
        }

        assertThat(definitions).containsExactly(OrganizationsCatalogFixtures.organizationCustomProperty());
        HTTP.verifyRequested(GET, DEFINITIONS_PATH);
    }

    @Test
    void createOrUpdateEnterpriseDefinitions_shouldReturnDefinitions_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, DEFINITIONS_PATH, okJson(jsonBytes(RESOURCES, DEFINITIONS_JSON)));

        final List<GitHubOrganizationCustomProperty> definitions;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesForOrgsApiClient api =
                enterpriseAdminCustomPropertiesForOrgsApi(HTTP)) {
            definitions = api.api()
                    .createOrUpdateEnterpriseDefinitions(
                            ENTERPRISE, EnterpriseAdminCatalogFixtures.createOrUpdateEnterpriseDefinitionsPayload());
        }

        assertThat(definitions).containsExactly(OrganizationsCatalogFixtures.organizationCustomProperty());
        HTTP.verifyRequested(PATCH, DEFINITIONS_PATH);
    }

    @Test
    void getEnterpriseDefinition_shouldReturnDefinition_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, DEFINITION_PATH, okJson(jsonBytes(RESOURCES, DEFINITION_JSON)));

        final Optional<GitHubOrganizationCustomProperty> definition;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesForOrgsApiClient api =
                enterpriseAdminCustomPropertiesForOrgsApi(HTTP)) {
            definition = api.api().getEnterpriseDefinition(ENTERPRISE, CUSTOM_PROPERTY_NAME);
        }

        assertThat(definition).contains(OrganizationsCatalogFixtures.organizationCustomProperty());
        HTTP.verifyRequested(GET, DEFINITION_PATH);
    }

    @Test
    void getEnterpriseDefinition_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, DEFINITION_PATH, notFound());

        final Optional<GitHubOrganizationCustomProperty> definition;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesForOrgsApiClient api =
                enterpriseAdminCustomPropertiesForOrgsApi(HTTP)) {
            definition = api.api().getEnterpriseDefinition(ENTERPRISE, CUSTOM_PROPERTY_NAME);
        }

        assertThat(definition).isEmpty();
    }

    @Test
    void createOrUpdateEnterpriseDefinition_shouldReturnDefinition_whenResponseIsOk() throws Exception {
        HTTP.stub(PUT, DEFINITION_PATH, okJson(jsonBytes(RESOURCES, DEFINITION_JSON)));

        final GitHubOrganizationCustomProperty definition;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesForOrgsApiClient api =
                enterpriseAdminCustomPropertiesForOrgsApi(HTTP)) {
            definition = api.api()
                    .createOrUpdateEnterpriseDefinition(
                            ENTERPRISE,
                            CUSTOM_PROPERTY_NAME,
                            OrganizationsCatalogFixtures.organizationCustomPropertyPayload());
        }

        assertThat(definition).isEqualTo(OrganizationsCatalogFixtures.organizationCustomProperty());
        HTTP.verifyRequested(PUT, DEFINITION_PATH);
    }

    @Test
    void deleteEnterpriseDefinition_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, DEFINITION_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesForOrgsApiClient api =
                enterpriseAdminCustomPropertiesForOrgsApi(HTTP)) {
            api.api().deleteEnterpriseDefinition(ENTERPRISE, CUSTOM_PROPERTY_NAME);
        }

        HTTP.verifyRequested(DELETE, DEFINITION_PATH);
    }

    @Test
    void getEnterpriseValues_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, VALUES_PATH, okJson(jsonBytes(RESOURCES, VALUES_JSON)));

        final GitHubPage<GitHubEnterpriseOrgPropertyValues> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesForOrgsApiClient api =
                enterpriseAdminCustomPropertiesForOrgsApi(HTTP)) {
            page = api.api().getEnterpriseValues(ENTERPRISE);
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.enterpriseOrgPropertyValues());
        HTTP.verifyRequested(GET, VALUES_PATH);
    }

    @Test
    void getEnterpriseValuesWithRequest_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, VALUES_PATH, okJson(jsonBytes(RESOURCES, VALUES_JSON)));

        final GitHubPage<GitHubEnterpriseOrgPropertyValues> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesForOrgsApiClient api =
                enterpriseAdminCustomPropertiesForOrgsApi(HTTP)) {
            page = api.api().getEnterpriseValues(ENTERPRISE, GitHubPageRequest.byPage());
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.enterpriseOrgPropertyValues());
        HTTP.verifyRequested(GET, VALUES_PATH);
    }

    @Test
    void createOrUpdateEnterpriseValues_shouldSendPatch_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PATCH, VALUES_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminCustomPropertiesForOrgsApiClient api =
                enterpriseAdminCustomPropertiesForOrgsApi(HTTP)) {
            api.api()
                    .createOrUpdateEnterpriseValues(
                            ENTERPRISE, EnterpriseAdminCatalogFixtures.createOrUpdateEnterpriseValuesPayload());
        }

        HTTP.verifyRequested(PATCH, VALUES_PATH);
    }
}
