package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubActivityStarringApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String USERNAME = "octocat";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActivityStarringApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listStargazersWithStarredAt_shouldSendStarMediaType_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("stargazer.json") + "]"));

        final GitHubPage<GitHubStargazer> page = api().listStargazersWithStarredAt(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.stargazer());
            softly.assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.star+json");
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/stargazers?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listStargazersForRepo_shouldRequestStargazersPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        "[" + FileUtils.readString(UsersCatalogFixtures.class, "simple-user-octocat.json") + "]"));

        final GitHubPage<GitHubSimpleUser> page = api().listStargazersForRepo(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(UsersCatalogFixtures.octocat());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/stargazers?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listReposStarredByUser_shouldRequestUserStarredPath_whenUsernameOnly() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listRepositoryJson()));

        final GitHubPage<GitHubRepository> page = api().listReposStarredByUser(USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.teamEnvironmentRepository());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/users/octocat/starred?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void getStargazerCountForRepo_shouldReturnCount_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("get-stargazer-count-for-repo-response.json")));

        final GitHubGetStargazerCountForRepoResponse count = api().getStargazerCountForRepo(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(count).isEqualTo(ActivityCatalogFixtures.getStargazerCountForRepoResponse());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/stargazers/count");
        });
    }

    @Test
    void getStargazerCountForRepo_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final GitHubActivityStarringApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getStargazerCountForRepo(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getStargazerHistoryForRepo_shouldReturnHistory_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("stargazer-history.json") + "]"));

        final GitHubPage<GitHubStargazerHistory> page = api().getStargazerHistoryForRepo(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.stargazerHistory());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/stargazers/history");
        });
    }

    @Test
    void getStargazerHistoryForRepo_shouldNotRequestNextPage_whenRequestIsNone() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("stargazer-history.json") + "]"));

        final GitHubPage<GitHubStargazerHistory> page =
                api().getStargazerHistoryForRepo(OWNER, REPO, GitHubPageRequest.none());

        assertThat(page.next()).isEmpty();
    }

    @Test
    void getStargazerHistoryForRepo_shouldThrow_whenRequestNull() {
        final GitHubActivityStarringApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getStargazerHistoryForRepo(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void listReposStarredByAuthenticatedUser_shouldRequestStarredPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        "[" + FileUtils.readString(RepositoriesCatalogFixtures.class, "repository.json") + "]"));

        final GitHubPage<GitHubRepository> page = api().listReposStarredByAuthenticatedUser(
                        ActivityCatalogFixtures.listReposStarredByAuthenticatedUserFilter(),
                        GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.teamEnvironmentRepository());
            softly.assertThat(requestUri.getPath()).isEqualTo("/user/starred");
        });
    }

    @Test
    void listReposStarredByAuthenticatedUserWithStarredAt_shouldSendStarMediaType_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("starred-repository.json") + "]"));

        final GitHubPage<GitHubStarredRepository> page =
                api().listReposStarredByAuthenticatedUserWithStarredAt(GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.starredRepository());
            softly.assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.star+json");
            softly.assertThat(request.uri().getPath()).isEqualTo("/user/starred");
        });
    }

    @Test
    void listReposStarredByAuthenticatedUserWithStarredAt_shouldSendStarMediaType_whenNoArguments() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("starred-repository.json") + "]"));

        final GitHubPage<GitHubStarredRepository> page = api().listReposStarredByAuthenticatedUserWithStarredAt();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.starredRepository());
            softly.assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.star+json");
            softly.assertThat(request.uri().getPath()).isEqualTo("/user/starred");
        });
    }

    @Test
    void checkRepoIsStarredByAuthenticatedUser_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean starred = api().checkRepoIsStarredByAuthenticatedUser(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(starred).isTrue();
            softly.assertThat(requestUri).hasToString("https://api.github.com/user/starred/octocat/Hello-World");
        });
    }

    @Test
    void starRepoForAuthenticatedUser_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().starRepoForAuthenticatedUser(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/starred/octocat/Hello-World");
        });
    }

    @Test
    void unstarRepoForAuthenticatedUser_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().unstarRepoForAuthenticatedUser(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/starred/octocat/Hello-World");
        });
    }

    @Test
    void listReposStarredByUserWithStarredAt_shouldSendStarMediaType_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("starred-repository.json") + "]"));

        final GitHubPage<GitHubStarredRepository> page = api().listReposStarredByUserWithStarredAt(USERNAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.starredRepository());
            softly.assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.star+json");
            softly.assertThat(request.uri().getPath()).isEqualTo("/users/octocat/starred");
        });
    }

    @Test
    void listStargazersWithStarredAt_shouldSendStarMediaType_whenDefaultRequest() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("stargazer.json") + "]"));

        final GitHubPage<GitHubStargazer> page = api().listStargazersWithStarredAt(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.stargazer());
            softly.assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.star+json");
        });
    }

    @Test
    void listReposStarredByAuthenticatedUser_shouldRequestStarredPath_whenNoArguments() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listRepositoryJson()));

        final GitHubPage<GitHubRepository> page = api().listReposStarredByAuthenticatedUser();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.teamEnvironmentRepository());
            softly.assertThat(requestUri.getPath()).isEqualTo("/user/starred");
        });
    }

    @Test
    void listReposStarredByAuthenticatedUser_shouldRequestStarredPath_whenFilterGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listRepositoryJson()));

        final GitHubPage<GitHubRepository> page = api().listReposStarredByAuthenticatedUser(
                        ActivityCatalogFixtures.listReposStarredByAuthenticatedUserFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.teamEnvironmentRepository());
            softly.assertThat(requestUri.getPath()).isEqualTo("/user/starred");
        });
    }

    @Test
    void listReposStarredByAuthenticatedUser_shouldRequestStarredPath_whenRequestGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listRepositoryJson()));

        final GitHubPage<GitHubRepository> page = api().listReposStarredByAuthenticatedUser(GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.teamEnvironmentRepository());
            softly.assertThat(requestUri.getPath()).isEqualTo("/user/starred");
        });
    }

    @Test
    void listReposStarredByAuthenticatedUserWithStarredAt_shouldSendStarMediaType_whenRequestGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("starred-repository.json") + "]"));

        final GitHubPage<GitHubStarredRepository> page =
                api().listReposStarredByAuthenticatedUserWithStarredAt(GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.starredRepository());
            softly.assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.star+json");
        });
    }

    @Test
    void listReposStarredByUser_shouldRequestUserStarredPath_whenRequestGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        "[" + FileUtils.readString(RepositoriesCatalogFixtures.class, "repository.json") + "]"));

        final GitHubPage<GitHubRepository> page = api().listReposStarredByUser(USERNAME, GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.teamEnvironmentRepository());
            softly.assertThat(requestUri.getPath()).isEqualTo("/users/octocat/starred");
        });
    }

    @Test
    void listReposStarredByUserWithStarredAt_shouldSendStarMediaType_whenUsernameOnly() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("starred-repository.json") + "]"));

        final GitHubPage<GitHubStarredRepository> page = api().listReposStarredByUserWithStarredAt(USERNAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.starredRepository());
            softly.assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.star+json");
        });
    }

    @Test
    void listReposStarredByUserWithStarredAt_shouldSendStarMediaType_whenRequestGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("starred-repository.json") + "]"));

        final GitHubPage<GitHubStarredRepository> page =
                api().listReposStarredByUserWithStarredAt(USERNAME, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.starredRepository());
            softly.assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.star+json");
        });
    }

    private GitHubActivityStarringApi api() {
        return new GitHubActivityStarringApi(TestClients.caller(transport));
    }

    private static String listRepositoryJson() {
        return "[" + FileUtils.readString(RepositoriesCatalogFixtures.class, "repository.json") + "]";
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActivityStarringApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
