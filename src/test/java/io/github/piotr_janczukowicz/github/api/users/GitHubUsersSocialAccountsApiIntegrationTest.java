package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.usersApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubUsersSocialAccountsApiIntegrationTest {

    private static final String USER_SOCIAL_ACCOUNTS_PATH = "/user/social_accounts";
    private static final String OCTOCAT_SOCIAL_ACCOUNTS_PATH = "/users/octocat/social_accounts";
    private static final GitHubUpdateSocialAccountsPayload UPDATE_PAYLOAD =
            UsersCatalogFixtures.updateSocialAccountsPayload();

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listSocialAccounts_shouldReturnAccounts_whenAuthenticatedUser() throws Exception {
        HTTP.stub(GET, USER_SOCIAL_ACCOUNTS_PATH, okJson(socialAccountsOneBytes()));

        final GitHubPage<GitHubSocialAccount> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().socialAccounts().listSocialAccounts();
        }

        assertThat(page.items()).isEqualTo(expectedSocialAccounts());
    }

    @Test
    void listSocialAccounts_shouldReturnAccounts_whenUsernameProvided() throws Exception {
        HTTP.stub(GET, OCTOCAT_SOCIAL_ACCOUNTS_PATH, okJson(socialAccountsOneBytes()));

        final GitHubPage<GitHubSocialAccount> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().socialAccounts().listSocialAccounts("octocat");
        }

        assertThat(page.items()).isEqualTo(expectedSocialAccounts());
    }

    @Test
    void addSocialAccounts_shouldReturnAccounts_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, USER_SOCIAL_ACCOUNTS_PATH, createdJson(socialAccountsOneBytes()));

        final List<GitHubSocialAccount> accounts;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            accounts = users.api().socialAccounts().addSocialAccounts(UPDATE_PAYLOAD);
        }

        assertThat(accounts).isEqualTo(expectedSocialAccounts());
        HTTP.verifyHeader(POST, USER_SOCIAL_ACCOUNTS_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void deleteSocialAccounts_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_SOCIAL_ACCOUNTS_PATH, noContent());

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().socialAccounts().deleteSocialAccounts(UPDATE_PAYLOAD);
        }

        HTTP.verifyHeader(DELETE, USER_SOCIAL_ACCOUNTS_PATH, CONTENT_TYPE, "application/json");
    }

    private static byte[] socialAccountsOneBytes() {
        return CODEC.encode(expectedSocialAccounts());
    }

    private static List<GitHubSocialAccount> expectedSocialAccounts() {
        final GitHubSocialAccount account = CODEC.decode(
                jsonBytes(GitHubUsersSocialAccountsApiIntegrationTest.class, "social-account.json"),
                GitHubSocialAccount.class);
        return List.of(account);
    }
}
