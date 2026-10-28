package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
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
class GitHubActivityEventsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ORG = "octo-org";
    private static final String USERNAME = "octocat";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActivityEventsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listPublicEvents_shouldRequestEventsPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson()));

        final GitHubPage<GitHubEvent> page = api().listPublicEvents();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.event());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/events?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listPublicEventsForRepoNetwork_shouldRequestNetworkPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson()));

        final GitHubPage<GitHubEvent> page = api().listPublicEventsForRepoNetwork(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.event());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/networks/octocat/Hello-World/events?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listPublicOrgEvents_shouldRequestOrgPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson()));

        final GitHubPage<GitHubEvent> page = api().listPublicOrgEvents(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.event());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octo-org/events?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listRepoEvents_shouldRequestRepoPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson()));

        final GitHubPage<GitHubEvent> page = api().listRepoEvents(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.event());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/events?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listEventsForAuthenticatedUser_shouldRequestUserPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson()));

        final GitHubPage<GitHubEvent> page = api().listEventsForAuthenticatedUser(USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.event());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/users/octocat/events?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listOrgEventsForAuthenticatedUser_shouldRequestUserOrgPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson()));

        final GitHubPage<GitHubEvent> page = api().listOrgEventsForAuthenticatedUser(USERNAME, ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.event());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/users/octocat/events/orgs/octo-org?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listPublicEventsForUser_shouldRequestUserPublicPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson()));

        final GitHubPage<GitHubEvent> page = api().listPublicEventsForUser(USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.event());
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/users/octocat/events/public?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listReceivedEventsForUser_shouldRequestReceivedPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson()));

        final GitHubPage<GitHubEvent> page = api().listReceivedEventsForUser(USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.event());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/users/octocat/received_events?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listReceivedPublicEventsForUser_shouldRequestReceivedPublicPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), listJson()));

        final GitHubPage<GitHubEvent> page = api().listReceivedPublicEventsForUser(USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ActivityCatalogFixtures.event());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/users/octocat/received_events/public?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    private GitHubActivityEventsApi api() {
        return new GitHubActivityEventsApi(TestClients.caller(transport));
    }

    private static String listJson() {
        return "[" + FileUtils.readString(GitHubActivityEventsApiTest.class, "event.json") + "]";
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
