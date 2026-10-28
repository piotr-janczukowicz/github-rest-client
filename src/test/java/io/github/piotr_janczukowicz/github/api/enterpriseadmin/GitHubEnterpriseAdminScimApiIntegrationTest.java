package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminScimApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminScimApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminScimApiIntegrationTest.class;
    private static final String GROUPS_PATH = "/scim/v2/enterprises/octo-enterprise/Groups";
    private static final String GROUP_PATH = GROUPS_PATH + "/24b28bbb-5fc4-4686-a153-a020debb1155";
    private static final String USERS_PATH = "/scim/v2/enterprises/octo-enterprise/Users";
    private static final String USER_PATH = USERS_PATH + "/2819c223-7f76-453a-919d-413861904646";
    private static final String GROUP_JSON = "scim-enterprise-group-response.json";
    private static final String GROUP_LIST_JSON = "scim-enterprise-group-list.json";
    private static final String USER_JSON = "scim-enterprise-user-response.json";
    private static final String USER_LIST_JSON = "scim-enterprise-user-list.json";
    private static final String GROUP_ID = "24b28bbb-5fc4-4686-a153-a020debb1155";
    private static final String USER_ID = "2819c223-7f76-453a-919d-413861904646";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listProvisionedGroups_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, GROUPS_PATH, okJson(jsonBytes(RESOURCES, GROUP_LIST_JSON)));

        final GitHubPage<GitHubScimEnterpriseGroupResponse> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminScimApiClient api = enterpriseAdminScimApi(HTTP)) {
            page = api.api().listProvisionedGroups(ENTERPRISE);
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.scimEnterpriseGroupResponse());
        HTTP.verifyRequested(GET, GROUPS_PATH);
        HTTP.verifyHeader(GET, GROUPS_PATH, "Accept", "application/scim+json");
    }

    @Test
    void provisionGroup_shouldReturnGroup_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, GROUPS_PATH, createdJson(jsonBytes(RESOURCES, GROUP_JSON)));

        final GitHubScimEnterpriseGroupResponse group;
        try (final GitHubApiWireMockSupport.EnterpriseAdminScimApiClient api = enterpriseAdminScimApi(HTTP)) {
            group = api.api().provisionGroup(ENTERPRISE, EnterpriseAdminCatalogFixtures.scimEnterpriseGroup());
        }

        assertThat(group).isEqualTo(EnterpriseAdminCatalogFixtures.scimEnterpriseGroupResponse());
        HTTP.verifyRequested(POST, GROUPS_PATH);
        HTTP.verifyHeader(POST, GROUPS_PATH, "Accept", "application/scim+json");
    }

    @Test
    void getProvisioningInformationForGroup_shouldReturnGroup_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, GROUP_PATH, okJson(jsonBytes(RESOURCES, GROUP_JSON)));

        final Optional<GitHubScimEnterpriseGroupResponse> group;
        try (final GitHubApiWireMockSupport.EnterpriseAdminScimApiClient api = enterpriseAdminScimApi(HTTP)) {
            group = api.api().getProvisioningInformationForGroup(ENTERPRISE, GROUP_ID);
        }

        assertThat(group).contains(EnterpriseAdminCatalogFixtures.scimEnterpriseGroupResponse());
        HTTP.verifyRequested(GET, GROUP_PATH);
        HTTP.verifyHeader(GET, GROUP_PATH, "Accept", "application/scim+json");
    }

    @Test
    void getProvisioningInformationForGroup_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, GROUP_PATH, notFound());

        final Optional<GitHubScimEnterpriseGroupResponse> group;
        try (final GitHubApiWireMockSupport.EnterpriseAdminScimApiClient api = enterpriseAdminScimApi(HTTP)) {
            group = api.api().getProvisioningInformationForGroup(ENTERPRISE, GROUP_ID);
        }

        assertThat(group).isEmpty();
    }

    @Test
    void setInformationForProvisionedGroup_shouldReturnGroup_whenResponseIsOk() throws Exception {
        HTTP.stub(PUT, GROUP_PATH, okJson(jsonBytes(RESOURCES, GROUP_JSON)));

        final GitHubScimEnterpriseGroupResponse group;
        try (final GitHubApiWireMockSupport.EnterpriseAdminScimApiClient api = enterpriseAdminScimApi(HTTP)) {
            group = api.api()
                    .setInformationForProvisionedGroup(
                            ENTERPRISE, GROUP_ID, EnterpriseAdminCatalogFixtures.scimEnterpriseGroup());
        }

        assertThat(group).isEqualTo(EnterpriseAdminCatalogFixtures.scimEnterpriseGroupResponse());
        HTTP.verifyRequested(PUT, GROUP_PATH);
        HTTP.verifyHeader(PUT, GROUP_PATH, "Accept", "application/scim+json");
    }

    @Test
    void updateAttributeForGroup_shouldReturnGroup_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, GROUP_PATH, okJson(jsonBytes(RESOURCES, GROUP_JSON)));

        final GitHubScimEnterpriseGroupResponse group;
        try (final GitHubApiWireMockSupport.EnterpriseAdminScimApiClient api = enterpriseAdminScimApi(HTTP)) {
            group = api.api()
                    .updateAttributeForGroup(ENTERPRISE, GROUP_ID, EnterpriseAdminCatalogFixtures.patchSchema());
        }

        assertThat(group).isEqualTo(EnterpriseAdminCatalogFixtures.scimEnterpriseGroupResponse());
        HTTP.verifyRequested(PATCH, GROUP_PATH);
        HTTP.verifyHeader(PATCH, GROUP_PATH, "Accept", "application/scim+json");
    }

    @Test
    void deleteScimGroup_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, GROUP_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminScimApiClient api = enterpriseAdminScimApi(HTTP)) {
            api.api().deleteScimGroup(ENTERPRISE, GROUP_ID);
        }

        HTTP.verifyRequested(DELETE, GROUP_PATH);

        HTTP.verifyHeader(DELETE, GROUP_PATH, "Accept", "application/scim+json");
    }

    @Test
    void listProvisionedIdentities_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, USERS_PATH, okJson(jsonBytes(RESOURCES, USER_LIST_JSON)));

        final GitHubPage<GitHubScimEnterpriseUserResponse> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminScimApiClient api = enterpriseAdminScimApi(HTTP)) {
            page = api.api().listProvisionedIdentities(ENTERPRISE);
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.scimEnterpriseUserResponse());
        HTTP.verifyRequested(GET, USERS_PATH);
        HTTP.verifyHeader(GET, USERS_PATH, "Accept", "application/scim+json");
    }

    @Test
    void provisionUser_shouldReturnUser_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, USERS_PATH, createdJson(jsonBytes(RESOURCES, USER_JSON)));

        final GitHubScimEnterpriseUserResponse user;
        try (final GitHubApiWireMockSupport.EnterpriseAdminScimApiClient api = enterpriseAdminScimApi(HTTP)) {
            user = api.api().provisionUser(ENTERPRISE, EnterpriseAdminCatalogFixtures.scimEnterpriseUser());
        }

        assertThat(user).isEqualTo(EnterpriseAdminCatalogFixtures.scimEnterpriseUserResponse());
        HTTP.verifyRequested(POST, USERS_PATH);
        HTTP.verifyHeader(POST, USERS_PATH, "Accept", "application/scim+json");
    }

    @Test
    void getProvisioningInformationForUser_shouldReturnUser_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, USER_PATH, okJson(jsonBytes(RESOURCES, USER_JSON)));

        final Optional<GitHubScimEnterpriseUserResponse> user;
        try (final GitHubApiWireMockSupport.EnterpriseAdminScimApiClient api = enterpriseAdminScimApi(HTTP)) {
            user = api.api().getProvisioningInformationForUser(ENTERPRISE, USER_ID);
        }

        assertThat(user).contains(EnterpriseAdminCatalogFixtures.scimEnterpriseUserResponse());
        HTTP.verifyRequested(GET, USER_PATH);
        HTTP.verifyHeader(GET, USER_PATH, "Accept", "application/scim+json");
    }

    @Test
    void getProvisioningInformationForUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, USER_PATH, notFound());

        final Optional<GitHubScimEnterpriseUserResponse> user;
        try (final GitHubApiWireMockSupport.EnterpriseAdminScimApiClient api = enterpriseAdminScimApi(HTTP)) {
            user = api.api().getProvisioningInformationForUser(ENTERPRISE, USER_ID);
        }

        assertThat(user).isEmpty();
    }

    @Test
    void setInformationForProvisionedUser_shouldReturnUser_whenResponseIsOk() throws Exception {
        HTTP.stub(PUT, USER_PATH, okJson(jsonBytes(RESOURCES, USER_JSON)));

        final GitHubScimEnterpriseUserResponse user;
        try (final GitHubApiWireMockSupport.EnterpriseAdminScimApiClient api = enterpriseAdminScimApi(HTTP)) {
            user = api.api()
                    .setInformationForProvisionedUser(
                            ENTERPRISE, USER_ID, EnterpriseAdminCatalogFixtures.scimEnterpriseUser());
        }

        assertThat(user).isEqualTo(EnterpriseAdminCatalogFixtures.scimEnterpriseUserResponse());
        HTTP.verifyRequested(PUT, USER_PATH);
        HTTP.verifyHeader(PUT, USER_PATH, "Accept", "application/scim+json");
    }

    @Test
    void updateAttributeForUser_shouldReturnUser_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, USER_PATH, okJson(jsonBytes(RESOURCES, USER_JSON)));

        final GitHubScimEnterpriseUserResponse user;
        try (final GitHubApiWireMockSupport.EnterpriseAdminScimApiClient api = enterpriseAdminScimApi(HTTP)) {
            user = api.api().updateAttributeForUser(ENTERPRISE, USER_ID, EnterpriseAdminCatalogFixtures.patchSchema());
        }

        assertThat(user).isEqualTo(EnterpriseAdminCatalogFixtures.scimEnterpriseUserResponse());
        HTTP.verifyRequested(PATCH, USER_PATH);
        HTTP.verifyHeader(PATCH, USER_PATH, "Accept", "application/scim+json");
    }

    @Test
    void deleteUser_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminScimApiClient api = enterpriseAdminScimApi(HTTP)) {
            api.api().deleteUser(ENTERPRISE, USER_ID);
        }

        HTTP.verifyRequested(DELETE, USER_PATH);

        HTTP.verifyHeader(DELETE, USER_PATH, "Accept", "application/scim+json");
    }
}
