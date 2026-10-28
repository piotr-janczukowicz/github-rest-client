package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.HttpHeaders;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpResponse;
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
class GitHubActivityNotificationsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long THREAD_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActivityNotificationsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listNotificationsForAuthenticatedUser_shouldRequestNotificationsPath_whenFilterGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson("thread.json")));

        final GitHubPage<GitHubThread> page = api().listNotificationsForAuthenticatedUser(
                        ActivityCatalogFixtures.listNotificationsFilter(), GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.thread());
            softly.assertThat(requestUri.getPath()).isEqualTo("/notifications");
        });
    }

    @Test
    void markNotificationsAsRead_shouldPutPayload_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("mark-notifications-as-read-response.json")));

        final Optional<GitHubMarkNotificationsAsReadResponse> response =
                api().markNotificationsAsRead(ActivityCatalogFixtures.markNotificationsAsReadPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(response).contains(ActivityCatalogFixtures.markNotificationsAsReadResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/notifications");
        });
    }

    @Test
    void getThread_shouldReturnThread_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("thread.json")));

        final GitHubThread thread = api().getThread(THREAD_ID).orElseThrow();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(thread).isEqualTo(ActivityCatalogFixtures.thread());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/notifications/threads/1");
        });
    }

    @Test
    void getThread_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubThread> result = api().getThread(THREAD_ID);

        assertThat(result).isEmpty();
    }

    @Test
    void markThreadAsRead_shouldSendPatch_whenResponseIsResetContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(205, null));

        api().markThreadAsRead(THREAD_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/notifications/threads/1");
        });
    }

    @Test
    void markThreadAsDone_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().markThreadAsDone(THREAD_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/notifications/threads/1");
        });
    }

    @Test
    void getThreadSubscriptionForAuthenticatedUser_shouldReturnSubscription_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("thread-subscription.json")));

        final GitHubThreadSubscription subscription =
                api().getThreadSubscriptionForAuthenticatedUser(THREAD_ID).orElseThrow();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(subscription).isEqualTo(ActivityCatalogFixtures.threadSubscription());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/notifications/threads/1/subscription");
        });
    }

    @Test
    void getThreadSubscriptionForAuthenticatedUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubThreadSubscription> result = api().getThreadSubscriptionForAuthenticatedUser(THREAD_ID);

        assertThat(result).isEmpty();
    }

    @Test
    void setThreadSubscription_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("thread-subscription.json")));

        final GitHubThreadSubscription subscription =
                api().setThreadSubscription(THREAD_ID, ActivityCatalogFixtures.setThreadSubscriptionPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(subscription).isEqualTo(ActivityCatalogFixtures.threadSubscription());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/notifications/threads/1/subscription");
        });
    }

    @Test
    void deleteThreadSubscription_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteThreadSubscription(THREAD_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/notifications/threads/1/subscription");
        });
    }

    @Test
    void listRepoNotificationsForAuthenticatedUser_shouldRequestRepoPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson("thread.json")));

        final GitHubPage<GitHubThread> page = api().listRepoNotificationsForAuthenticatedUser(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.thread());
            softly.assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/Hello-World/notifications");
        });
    }

    @Test
    void markRepoNotificationsAsRead_shouldPutPayload_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.ACCEPTED.code(), json("mark-repo-notifications-as-read-response.json")));

        final Optional<GitHubMarkRepoNotificationsAsReadResponse> response = api().markRepoNotificationsAsRead(
                        OWNER, REPO, ActivityCatalogFixtures.markRepoNotificationsAsReadPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(response).contains(ActivityCatalogFixtures.markRepoNotificationsAsReadResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/notifications");
        });
    }

    @Test
    void listNotificationsForAuthenticatedUser_shouldRequestNotificationsPath_whenNoArguments() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson("thread.json")));

        final GitHubPage<GitHubThread> page = api().listNotificationsForAuthenticatedUser();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.thread());
            softly.assertThat(requestUri.getPath()).isEqualTo("/notifications");
        });
    }

    @Test
    void listNotificationsForAuthenticatedUser_shouldRequestNotificationsPath_whenFilterGivenOnly() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson("thread.json")));

        final GitHubPage<GitHubThread> page =
                api().listNotificationsForAuthenticatedUser(ActivityCatalogFixtures.listNotificationsFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.thread());
            softly.assertThat(requestUri.getPath()).isEqualTo("/notifications");
        });
    }

    @Test
    void listNotificationsForAuthenticatedUser_shouldRequestNotificationsPath_whenRequestGivenOnly() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson("thread.json")));

        final GitHubPage<GitHubThread> page = api().listNotificationsForAuthenticatedUser(GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.thread());
            softly.assertThat(requestUri.getPath()).isEqualTo("/notifications");
        });
    }

    @Test
    void listNotificationsForAuthenticatedUser_shouldThrow_whenPerPageAboveFifty() {
        final GitHubActivityNotificationsApi api = api();

        final Throwable thrown =
                catchThrowable(() -> api.listNotificationsForAuthenticatedUser(GitHubPageNumberRequest.of(1, 51)));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("50");
    }

    @Test
    void listNotificationsForAuthenticatedUser_shouldThrow_whenRequestIsCursor() {
        final GitHubActivityNotificationsApi api = api();

        final Throwable thrown =
                catchThrowable(() -> api.listNotificationsForAuthenticatedUser(GitHubPageRequest.byCursor()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void listRepoNotificationsForAuthenticatedUser_shouldThrow_whenRequestIsCursor() {
        final GitHubActivityNotificationsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listRepoNotificationsForAuthenticatedUser(
                "octocat",
                "hello",
                GitHubListRepoNotificationsForAuthenticatedUserFilter.empty(),
                GitHubPageRequest.byCursor()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void markNotificationsAsRead_shouldReturnEmpty_whenResponseIsResetContent() throws Exception {
        when(transport.execute(any())).thenReturn(new HttpResponse(205, HttpHeaders.empty(), HttpBody.absent()));

        final Optional<GitHubMarkNotificationsAsReadResponse> response = api().markNotificationsAsRead();

        assertThat(response).isEmpty();
    }

    @Test
    void markRepoNotificationsAsRead_shouldReturnEmpty_whenResponseIsResetContent() throws Exception {
        when(transport.execute(any())).thenReturn(new HttpResponse(205, HttpHeaders.empty(), HttpBody.absent()));

        final Optional<GitHubMarkRepoNotificationsAsReadResponse> response =
                api().markRepoNotificationsAsRead(OWNER, REPO);

        assertThat(response).isEmpty();
    }

    @Test
    void markNotificationsAsRead_shouldSendPut_whenNoPayloadGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("mark-notifications-as-read-response.json")));

        final Optional<GitHubMarkNotificationsAsReadResponse> response = api().markNotificationsAsRead();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(response).contains(ActivityCatalogFixtures.markNotificationsAsReadResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
        });
    }

    @Test
    void setThreadSubscription_shouldSendPut_whenNoPayloadGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("thread-subscription.json")));

        final GitHubThreadSubscription subscription = api().setThreadSubscription(THREAD_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(subscription).isEqualTo(ActivityCatalogFixtures.threadSubscription());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
        });
    }

    @Test
    void listRepoNotificationsForAuthenticatedUser_shouldRequestRepoPath_whenFilterGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson("thread.json")));

        final GitHubPage<GitHubThread> page = api().listRepoNotificationsForAuthenticatedUser(
                        OWNER, REPO, ActivityCatalogFixtures.listRepoNotificationsFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.thread());
            softly.assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/Hello-World/notifications");
        });
    }

    @Test
    void listRepoNotificationsForAuthenticatedUser_shouldRequestRepoPath_whenRequestGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson("thread.json")));

        final GitHubPage<GitHubThread> page =
                api().listRepoNotificationsForAuthenticatedUser(OWNER, REPO, GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.thread());
            softly.assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/Hello-World/notifications");
        });
    }

    @Test
    void markRepoNotificationsAsRead_shouldSendPut_whenNoPayloadGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.ACCEPTED.code(), json("mark-repo-notifications-as-read-response.json")));

        final Optional<GitHubMarkRepoNotificationsAsReadResponse> response =
                api().markRepoNotificationsAsRead(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(response).contains(ActivityCatalogFixtures.markRepoNotificationsAsReadResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
        });
    }

    private GitHubActivityNotificationsApi api() {
        return new GitHubActivityNotificationsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActivityNotificationsApiTest.class, fileName);
    }

    private static String listJson(final String fileName) {
        return "[" + json(fileName) + "]";
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
