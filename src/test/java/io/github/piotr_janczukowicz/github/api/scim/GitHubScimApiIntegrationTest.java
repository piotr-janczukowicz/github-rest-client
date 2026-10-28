package io.github.piotr_janczukowicz.github.api.scim;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.scimApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubStartIndexRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubScimApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String SCIM_USER_ID = "edefdfedf-050c-11e7-8d32";
    private static final String USERS_PATH = "/scim/v2/organizations/octo-org/Users";
    private static final String USER_PATH = "/scim/v2/organizations/octo-org/Users/edefdfedf-050c-11e7-8d32";
    private static final String USER_JSON = "scim-user.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listProvisionedIdentities_shouldFollowStartIndex_whenBodyTotalAllowsNextPage() throws Exception {
        HTTP.stub(
                GET,
                USERS_PATH,
                okJson(jsonBytes(GitHubScimApiIntegrationTest.class, "scim-user-list-first-page.json")),
                okJson(jsonBytes(GitHubScimApiIntegrationTest.class, "scim-user-list-last-page.json")));

        final GitHubPage<GitHubScimUser> first;
        final Optional<GitHubPage<GitHubScimUser>> second;
        try (final GitHubApiWireMockSupport.ScimApiClient api = scimApi(HTTP)) {
            first = api.api().listProvisionedIdentities(ORG);
            second = first.next();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).hasSize(2);
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactly(ScimCatalogFixtures.scimUser());
        });
        HTTP.verifyQueryParam(GET, USERS_PATH, "startIndex", "1");
        HTTP.verifyQueryParam(GET, USERS_PATH, "startIndex", "3");
        HTTP.verifyRequested(GET, USERS_PATH, 2);
        HTTP.verifyHeader(GET, USERS_PATH, "Accept", "application/scim+json");
    }

    @Test
    void listProvisionedIdentities_shouldSendFilterAndCountOnEveryPage_whenFilterHasSpecialCharacters()
            throws Exception {
        final String expression = "userName eq \"a+b@x.com\"";
        HTTP.stub(
                GET,
                USERS_PATH,
                okJson(jsonBytes(GitHubScimApiIntegrationTest.class, "scim-user-list-first-page.json"))
                        .header("Link", "<http://evil.example/scim?startIndex=99>; rel=\"next\""),
                okJson(jsonBytes(GitHubScimApiIntegrationTest.class, "scim-user-list-last-page.json")));

        final GitHubPage<GitHubScimUser> first;
        final Optional<GitHubPage<GitHubScimUser>> second;
        try (final GitHubApiWireMockSupport.ScimApiClient api = scimApi(HTTP)) {
            first = api.api()
                    .listProvisionedIdentities(
                            ORG,
                            new GitHubListProvisionedIdentitiesFilter(expression),
                            GitHubStartIndexRequest.of(1, 2));
            second = first.next();
        }

        assertThat(second).isPresent();
        HTTP.verifyQueryParam(GET, USERS_PATH, "filter", expression);
        HTTP.verifyQueryParam(GET, USERS_PATH, "count", "2");
        HTTP.verifyQueryParam(GET, USERS_PATH, "startIndex", "1");
        HTTP.verifyQueryParam(GET, USERS_PATH, "startIndex", "3");
        HTTP.verifyNoQueryParam(GET, USERS_PATH, "per_page");
        HTTP.verifyRequested(GET, USERS_PATH, 2);
        HTTP.verifyHeader(GET, USERS_PATH, "Accept", "application/scim+json");
    }

    @Test
    void getProvisioningInformationForUser_shouldReturnUser_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, USER_PATH, okJson(jsonBytes(GitHubScimApiIntegrationTest.class, USER_JSON)));

        final Optional<GitHubScimUser> user;
        try (final GitHubApiWireMockSupport.ScimApiClient api = scimApi(HTTP)) {
            user = api.api().getProvisioningInformationForUser(ORG, SCIM_USER_ID);
        }

        assertThat(user).contains(ScimCatalogFixtures.scimUser());
        HTTP.verifyRequested(GET, USER_PATH);
        HTTP.verifyHeader(GET, USER_PATH, "Accept", "application/scim+json");
    }

    @Test
    void getProvisioningInformationForUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, USER_PATH, notFound());

        final Optional<GitHubScimUser> user;
        try (final GitHubApiWireMockSupport.ScimApiClient api = scimApi(HTTP)) {
            user = api.api().getProvisioningInformationForUser(ORG, SCIM_USER_ID);
        }

        assertThat(user).isEmpty();
    }

    @Test
    void provisionAndInviteUser_shouldReturnUser_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, USERS_PATH, createdJson(jsonBytes(GitHubScimApiIntegrationTest.class, USER_JSON)));

        final GitHubScimUser user;
        try (final GitHubApiWireMockSupport.ScimApiClient api = scimApi(HTTP)) {
            user = api.api().provisionAndInviteUser(ORG, ScimCatalogFixtures.provisionAndInviteUserPayload());
        }

        assertThat(user).isEqualTo(ScimCatalogFixtures.scimUser());
        HTTP.verifyRequested(POST, USERS_PATH);
        HTTP.verifyHeader(POST, USERS_PATH, "Accept", "application/scim+json");
    }

    @Test
    void setInformationForProvisionedUser_shouldReturnUser_whenResponseIsOk() throws Exception {
        HTTP.stub(PUT, USER_PATH, okJson(jsonBytes(GitHubScimApiIntegrationTest.class, USER_JSON)));

        final GitHubScimUser user;
        try (final GitHubApiWireMockSupport.ScimApiClient api = scimApi(HTTP)) {
            user = api.api()
                    .setInformationForProvisionedUser(
                            ORG, SCIM_USER_ID, ScimCatalogFixtures.setInformationForProvisionedUserPayload());
        }

        assertThat(user).isEqualTo(ScimCatalogFixtures.scimUser());
        HTTP.verifyRequested(PUT, USER_PATH);
        HTTP.verifyHeader(PUT, USER_PATH, "Accept", "application/scim+json");
    }

    @Test
    void updateAttributeForUser_shouldReturnUser_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, USER_PATH, okJson(jsonBytes(GitHubScimApiIntegrationTest.class, USER_JSON)));

        final GitHubScimUser user;
        try (final GitHubApiWireMockSupport.ScimApiClient api = scimApi(HTTP)) {
            user = api.api()
                    .updateAttributeForUser(ORG, SCIM_USER_ID, ScimCatalogFixtures.updateAttributeForUserPayload());
        }

        assertThat(user).isEqualTo(ScimCatalogFixtures.scimUser());
        HTTP.verifyRequested(PATCH, USER_PATH);
        HTTP.verifyHeader(PATCH, USER_PATH, "Accept", "application/scim+json");
    }

    @Test
    void deleteUserFromOrg_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_PATH, noContent());

        try (final GitHubApiWireMockSupport.ScimApiClient api = scimApi(HTTP)) {
            api.api().deleteUserFromOrg(ORG, SCIM_USER_ID);
        }

        HTTP.verifyRequested(DELETE, USER_PATH);

        HTTP.verifyHeader(DELETE, USER_PATH, "Accept", "application/scim+json");
    }
}
