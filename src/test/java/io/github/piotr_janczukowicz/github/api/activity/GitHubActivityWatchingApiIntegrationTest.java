package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.activityApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositorySubscription;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActivityWatchingApiIntegrationTest {

    private static final String SUBSCRIPTION_PATH = "/repos/octocat/Hello-World/subscription";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getRepoSubscription_shouldReturnSubscription_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                SUBSCRIPTION_PATH,
                okJson(jsonBytes(RepositoriesCatalogFixtures.class, "repository-subscription.json")));

        final Optional<GitHubRepositorySubscription> subscription;
        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            subscription = api.api().watching().getRepoSubscription("octocat", "Hello-World");
        }

        assertThat(subscription).contains(RepositoriesCatalogFixtures.repositorySubscription());
        HTTP.verifyRequested(GET, SUBSCRIPTION_PATH);
    }

    @Test
    void getRepoSubscription_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, SUBSCRIPTION_PATH, notFound());

        final Optional<GitHubRepositorySubscription> subscription;
        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            subscription = api.api().watching().getRepoSubscription("octocat", "Hello-World");
        }

        assertThat(subscription).isEmpty();
    }

    @Test
    void setRepoSubscription_shouldSendPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                SUBSCRIPTION_PATH,
                okJson(jsonBytes(RepositoriesCatalogFixtures.class, "repository-subscription.json")));
        final GitHubSetRepoSubscriptionPayload payload = ActivityCatalogFixtures.setRepoSubscriptionPayload();

        final GitHubRepositorySubscription subscription;
        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            subscription = api.api().watching().setRepoSubscription("octocat", "Hello-World", payload);
        }

        assertThat(subscription).isEqualTo(RepositoriesCatalogFixtures.repositorySubscription());
        HTTP.verifyBody(PUT, SUBSCRIPTION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }
}
