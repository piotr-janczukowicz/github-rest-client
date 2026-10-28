package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.accepted;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.codespacesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCodespacesOrganizationsApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String USERNAME = "johnDoe";
    private static final String CODESPACE_NAME = "johnDoe-octo-abc123";
    private static final String ORG_CODESPACES_PATH = "/orgs/octo-org/codespaces";
    private static final String ORG_ACCESS_PATH = ORG_CODESPACES_PATH + "/access";
    private static final String ORG_SELECTED_USERS_PATH = ORG_ACCESS_PATH + "/selected_users";
    private static final String ORG_MEMBER_CODESPACE_PATH =
            "/orgs/octo-org/members/johnDoe/codespaces/johnDoe-octo-abc123";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listInOrganization_shouldReturnCodespaces_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_CODESPACES_PATH,
                okJson(jsonBytes(GitHubCodespacesOrganizationsApiIntegrationTest.class, "codespaces-page.json")));

        final GitHubPage<GitHubCodespace> page;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            page = codespaces.api().organizations().listInOrganization(ORG);
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        CodespacesCatalogFixtures.codespacesPage().codespaces());
        HTTP.verifyRequested(GET, ORG_CODESPACES_PATH);
        HTTP.verifyQueryParam(GET, ORG_CODESPACES_PATH, "page", "1");
    }

    @Test
    void setCodespacesAccess_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ORG_ACCESS_PATH, noContent());

        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespaces
                    .api()
                    .organizations()
                    .setCodespacesAccess(ORG, CodespacesCatalogFixtures.setCodespacesAccessPayload());
        }

        HTTP.verifyRequested(PUT, ORG_ACCESS_PATH);
        HTTP.verifyHeader(PUT, ORG_ACCESS_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void setAndDeleteCodespacesAccessUsers_shouldSendPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, ORG_SELECTED_USERS_PATH, noContent());
        HTTP.stub(DELETE, ORG_SELECTED_USERS_PATH, noContent());

        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespaces
                    .api()
                    .organizations()
                    .setCodespacesAccessUsers(ORG, CodespacesCatalogFixtures.setCodespacesAccessUsersPayload());
            codespaces
                    .api()
                    .organizations()
                    .deleteCodespacesAccessUsers(ORG, CodespacesCatalogFixtures.deleteCodespacesAccessUsersPayload());
        }

        HTTP.verifyRequested(POST, ORG_SELECTED_USERS_PATH);
        HTTP.verifyRequested(DELETE, ORG_SELECTED_USERS_PATH);
    }

    @Test
    void listForUserInOrganization_shouldReturnCodespaces_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/orgs/octo-org/members/johnDoe/codespaces",
                okJson(jsonBytes(GitHubCodespacesOrganizationsApiIntegrationTest.class, "codespaces-page.json")));

        final GitHubPage<GitHubCodespace> page;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            page = codespaces.api().organizations().listForUserInOrganization(ORG, USERNAME);
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        CodespacesCatalogFixtures.codespacesPage().codespaces());
        HTTP.verifyRequested(GET, "/orgs/octo-org/members/johnDoe/codespaces");
    }

    @Test
    void deleteFromOrganization_shouldDelete_whenResponseIsAccepted() throws Exception {
        HTTP.stub(DELETE, ORG_MEMBER_CODESPACE_PATH, accepted());

        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespaces.api().organizations().deleteFromOrganization(ORG, USERNAME, CODESPACE_NAME);
        }

        HTTP.verifyRequested(DELETE, ORG_MEMBER_CODESPACE_PATH);
    }

    @Test
    void stopInOrganization_shouldReturnCodespace_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                ORG_MEMBER_CODESPACE_PATH + "/stop",
                okJson(jsonBytes(GitHubCodespacesOrganizationsApiIntegrationTest.class, "codespace.json")));

        final GitHubCodespace codespace;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespace = codespaces.api().organizations().stopInOrganization(ORG, USERNAME, CODESPACE_NAME);
        }

        assertThat(codespace).isEqualTo(CodespacesCatalogFixtures.codespace());
        HTTP.verifyRequested(POST, ORG_MEMBER_CODESPACE_PATH + "/stop");
    }
}
