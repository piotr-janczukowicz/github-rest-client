package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositorySubscription;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubActivityWatchingApiTest {

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
        final Throwable thrown = catchThrowable(() -> new GitHubActivityWatchingApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listWatchersForRepo_shouldRequestSubscribersPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        "[" + FileUtils.readString(UsersCatalogFixtures.class, "simple-user-octocat.json") + "]"));

        final GitHubPage<GitHubSimpleUser> page = api().listWatchersForRepo(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(UsersCatalogFixtures.octocat());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/subscribers?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void getRepoSubscription_shouldReturnSubscription_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        FileUtils.readString(RepositoriesCatalogFixtures.class, "repository-subscription.json")));

        final Optional<GitHubRepositorySubscription> subscription = api().getRepoSubscription(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(subscription).contains(RepositoriesCatalogFixtures.repositorySubscription());
            softly.assertThat(requestUri).hasToString("https://api.github.com/repos/octocat/Hello-World/subscription");
        });
    }

    @Test
    void getRepoSubscription_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubRepositorySubscription> subscription = api().getRepoSubscription(OWNER, REPO);

        assertThat(subscription).isEmpty();
    }

    @Test
    void setRepoSubscription_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        FileUtils.readString(RepositoriesCatalogFixtures.class, "repository-subscription.json")));

        final GitHubRepositorySubscription subscription =
                api().setRepoSubscription(OWNER, REPO, ActivityCatalogFixtures.setRepoSubscriptionPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(subscription).isEqualTo(RepositoriesCatalogFixtures.repositorySubscription());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/subscription");
        });
    }

    @Test
    void setRepoSubscription_shouldPutWithoutBody_whenNoPayloadGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        FileUtils.readString(RepositoriesCatalogFixtures.class, "repository-subscription.json")));

        final GitHubRepositorySubscription subscription = api().setRepoSubscription(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(subscription).isEqualTo(RepositoriesCatalogFixtures.repositorySubscription());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
        });
    }

    @Test
    void deleteRepoSubscription_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteRepoSubscription(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/subscription");
        });
    }

    @Test
    void listWatchedReposForAuthenticatedUser_shouldRequestUserSubscriptionsPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        "[" + FileUtils.readString(RepositoriesCatalogFixtures.class, "minimal-repository.json")
                                + "]"));

        final GitHubPage<GitHubMinimalRepository> page = api().listWatchedReposForAuthenticatedUser();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.helloWorldMinimalRepository());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/user/subscriptions?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listReposWatchedByUser_shouldRequestUserSubscriptionsPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        "[" + FileUtils.readString(RepositoriesCatalogFixtures.class, "minimal-repository.json")
                                + "]"));

        final GitHubPage<GitHubMinimalRepository> page = api().listReposWatchedByUser(USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.helloWorldMinimalRepository());
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/users/octocat/subscriptions?page=1&" + TestClients.perPageQuery());
        });
    }

    private GitHubActivityWatchingApi api() {
        return new GitHubActivityWatchingApi(TestClients.caller(transport));
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
