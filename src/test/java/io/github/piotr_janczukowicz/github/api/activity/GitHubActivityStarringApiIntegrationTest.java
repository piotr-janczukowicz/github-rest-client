package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.activityApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActivityStarringApiIntegrationTest {

    private static final String COUNT_PATH = "/repos/octocat/Hello-World/stargazers/count";
    private static final String HISTORY_PATH = "/repos/octocat/Hello-World/stargazers/history";
    private static final String STARRED_PATH = "/user/starred/octocat/Hello-World";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listStargazersForRepo_shouldReturnUsers_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/repos/octocat/Hello-World/stargazers",
                okJson(listJsonBytes(UsersCatalogFixtures.class, "simple-user-octocat.json")));

        final GitHubPage<GitHubSimpleUser> page;
        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            page = api.api().starring().listStargazersForRepo("octocat", "Hello-World");
        }

        assertThat(page.items()).containsExactly(UsersCatalogFixtures.octocat());
        HTTP.verifyQueryParam(GET, "/repos/octocat/Hello-World/stargazers", "per_page", "100");
    }

    @Test
    void listReposStarredByUser_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/users/octocat/starred",
                okJson(listJsonBytes(RepositoriesCatalogFixtures.class, "repository.json")));

        final GitHubPage<GitHubRepository> page;
        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            page = api.api().starring().listReposStarredByUser("octocat");
        }

        assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.teamEnvironmentRepository());
        HTTP.verifyQueryParam(GET, "/users/octocat/starred", "page", "1");
    }

    @Test
    void getStargazerCountForRepo_shouldReturnCount_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COUNT_PATH,
                okJson(jsonBytes(
                        GitHubActivityStarringApiIntegrationTest.class, "get-stargazer-count-for-repo-response.json")));

        final GitHubGetStargazerCountForRepoResponse count;
        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            count = api.api().starring().getStargazerCountForRepo("octocat", "Hello-World");
        }

        assertThat(count).isEqualTo(ActivityCatalogFixtures.getStargazerCountForRepoResponse());
        HTTP.verifyRequested(GET, COUNT_PATH);
    }

    @Test
    void getStargazerHistoryForRepo_shouldReturnHistory_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                HISTORY_PATH,
                okJson(listJsonBytes(GitHubActivityStarringApiIntegrationTest.class, "stargazer-history.json")));

        final GitHubPage<GitHubStargazerHistory> page;
        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            page = api.api().starring().getStargazerHistoryForRepo("octocat", "Hello-World");
        }

        assertThat(page.items()).containsExactly(ActivityCatalogFixtures.stargazerHistory());
        HTTP.verifyQueryParam(GET, HISTORY_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, HISTORY_PATH, "per_page", String.valueOf(Math.min(100, 30)));
    }

    @Test
    void getStargazerCountForRepo_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, COUNT_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            final GitHubActivityStarringApi starring = api.api().starring();

            thrown = catchThrowable(() -> starring.getStargazerCountForRepo("octocat", "Hello-World"));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void checkRepoIsStarredByAuthenticatedUser_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, STARRED_PATH, notFound());

        final boolean starred;
        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            starred = api.api().starring().checkRepoIsStarredByAuthenticatedUser("octocat", "Hello-World");
        }

        assertThat(starred).isFalse();
        HTTP.verifyRequested(GET, STARRED_PATH);
    }

    @Test
    void starRepoForAuthenticatedUser_shouldSendPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, STARRED_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            api.api().starring().starRepoForAuthenticatedUser("octocat", "Hello-World");
        }

        HTTP.verifyRequested(PUT, STARRED_PATH);
    }
}
