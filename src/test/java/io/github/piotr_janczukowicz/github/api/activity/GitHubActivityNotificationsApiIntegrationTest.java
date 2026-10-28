package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.acceptedJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.activityApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActivityNotificationsApiIntegrationTest {

    private static final String NOTIFICATIONS_PATH = "/notifications";
    private static final String THREAD_PATH = "/notifications/threads/1";
    private static final String THREAD_SUBSCRIPTION_PATH = "/notifications/threads/1/subscription";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listNotificationsForAuthenticatedUser_shouldSendFilterQuery_whenFilterGiven() throws Exception {
        HTTP.stub(
                GET,
                NOTIFICATIONS_PATH,
                okJson(listJsonBytes(GitHubActivityNotificationsApiIntegrationTest.class, "thread.json")));

        final GitHubPage<GitHubThread> page;
        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            page = api.api()
                    .notifications()
                    .listNotificationsForAuthenticatedUser(ActivityCatalogFixtures.listNotificationsFilter());
        }

        assertThat(page.items()).containsExactly(ActivityCatalogFixtures.thread());
        HTTP.verifyRequested(GET, NOTIFICATIONS_PATH);
        HTTP.verifyQueryParam(GET, NOTIFICATIONS_PATH, "all", "true");
    }

    @Test
    void markNotificationsAsRead_shouldSendPayload_whenResponseIsAccepted() throws Exception {
        HTTP.stub(
                PUT,
                NOTIFICATIONS_PATH,
                acceptedJson(jsonBytes(
                        GitHubActivityNotificationsApiIntegrationTest.class,
                        "mark-notifications-as-read-response.json")));
        final GitHubMarkNotificationsAsReadPayload payload = ActivityCatalogFixtures.markNotificationsAsReadPayload();

        final Optional<GitHubMarkNotificationsAsReadResponse> response;
        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            response = api.api().notifications().markNotificationsAsRead(payload);
        }

        assertThat(response).contains(ActivityCatalogFixtures.markNotificationsAsReadResponse());
        HTTP.verifyRequested(PUT, NOTIFICATIONS_PATH);
        HTTP.verifyBody(PUT, NOTIFICATIONS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void markThreadAsDone_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, THREAD_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            api.api().notifications().markThreadAsDone(1L);
        }

        HTTP.verifyRequested(DELETE, THREAD_PATH);
    }

    @Test
    void setThreadSubscription_shouldSendPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                THREAD_SUBSCRIPTION_PATH,
                okJson(jsonBytes(GitHubActivityNotificationsApiIntegrationTest.class, "thread-subscription.json")));
        final GitHubSetThreadSubscriptionPayload payload = ActivityCatalogFixtures.setThreadSubscriptionPayload();

        final GitHubThreadSubscription subscription;
        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            subscription = api.api().notifications().setThreadSubscription(1L, payload);
        }

        assertThat(subscription).isEqualTo(ActivityCatalogFixtures.threadSubscription());
        HTTP.verifyBody(PUT, THREAD_SUBSCRIPTION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }
}
