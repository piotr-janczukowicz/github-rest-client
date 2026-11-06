package io.github.piotr_janczukowicz.github.api.generic;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.RETRY_AFTER;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpStatus.NOT_FOUND;
import static io.github.piotr_janczukowicz.http.HttpStatus.NO_CONTENT;
import static io.github.piotr_janczukowicz.http.HttpStatus.OK;
import static io.github.piotr_janczukowicz.http.HttpStatus.TOO_MANY_REQUESTS;
import static io.github.piotr_janczukowicz.test.http.StubHttpServer.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.GitHubHeaders;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.GitHubClientConfiguration;
import io.github.piotr_janczukowicz.github.client.GitHubClients;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpHeaders;
import io.github.piotr_janczukowicz.http.HttpResponse;
import io.github.piotr_janczukowicz.http.transport.RetryConfiguration;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubGenericApiIntegrationTest {

    private static final String ITEMS_PATH = "/items";
    private static final JsonType<List<String>> STRINGS = JsonTypes.listOf(String.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void fetch_shouldDecodeBody_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ITEMS_PATH, jsonOk("[\"a\",\"b\"]"));

        final List<String> items;
        try (final GitHubClient client = client()) {
            items = api(client).fetch(GitHubGenericRequest.get(ITEMS_PATH), STRINGS);
        }

        assertThat(items).containsExactly("a", "b");
    }

    @Test
    void fetch_shouldThrowNotFound_whenStatusIs404() {
        HTTP.stub(GET, ITEMS_PATH, status(NOT_FOUND).body(bytes("{\"message\":\"Not Found\"}")));
        final GitHubGenericRequest request = GitHubGenericRequest.get(ITEMS_PATH);

        final Throwable thrown;
        try (final GitHubClient client = client()) {
            final GitHubGenericApi api = api(client);

            thrown = catchThrowable(() -> api.fetch(request, STRINGS));
        }

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void fetch_shouldRetry_whenRateLimited() throws Exception {
        HTTP.stub(GET, ITEMS_PATH, status(TOO_MANY_REQUESTS).header(RETRY_AFTER, "0"), jsonOk("[\"a\"]"));

        final List<String> items;
        try (final GitHubClient client = client()) {
            items = api(client).fetch(GitHubGenericRequest.get(ITEMS_PATH), STRINGS);
        }

        assertThat(items).containsExactly("a");
        HTTP.verifyRequested(GET, ITEMS_PATH, 2);
    }

    @Test
    void fetch_shouldSendPinnedApiVersionAndCustomHeader_whenRequestHasHeaders() throws Exception {
        HTTP.stub(GET, ITEMS_PATH, jsonOk("[]"));
        final GitHubGenericRequest request =
                GitHubGenericRequest.get(ITEMS_PATH).withHeaders(HttpHeaders.of("X-Custom", "1"));

        try (final GitHubClient client = client()) {
            api(client).fetch(request, STRINGS);
        }

        HTTP.verifyHeader(GET, ITEMS_PATH, GitHubHeaders.API_VERSION, GitHubClient.API_VERSION);
        HTTP.verifyHeader(GET, ITEMS_PATH, "X-Custom", "1");
    }

    @Test
    void fetch_shouldSendJsonBody_whenPostWithJsonBody() throws Exception {
        HTTP.stub(POST, ITEMS_PATH, jsonOk("[]"));
        final GitHubGenericRequest request =
                GitHubGenericRequest.post(ITEMS_PATH).withJsonBody(Map.of("a", 1));

        try (final GitHubClient client = client()) {
            api(client).fetch(request, STRINGS);
        }

        HTTP.verifyBody(POST, ITEMS_PATH, "{\"a\":1}");
    }

    @Test
    void send_shouldReturnNoContent_whenStatusIs204() throws Exception {
        HTTP.stub(GET, ITEMS_PATH, status(NO_CONTENT));

        final HttpResponse response;
        try (final GitHubClient client = client()) {
            response = api(client).send(GitHubGenericRequest.get(ITEMS_PATH));
        }

        assertThat(response.hasStatus(NO_CONTENT)).isTrue();
    }

    @Test
    void list_shouldWalkPages_whenLinkHeaderPresent() throws Exception {
        HTTP.stub(
                GET,
                ITEMS_PATH,
                jsonOk("[\"a\"]")
                        .header(LINK, "<" + ITEMS_PATH + "?page=2&" + TestClients.perPageQuery() + ">; rel=\"next\""),
                jsonOk("[\"b\"]"));

        final GitHubPage<String> first;
        final Optional<GitHubPage<String>> second;
        try (final GitHubClient client = client()) {
            first = api(client).list(GitHubGenericRequest.get(ITEMS_PATH), GitHubPageRequest.byPage(), STRINGS);
            second = first.next();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactly("a");
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactly("b");
        });
        HTTP.verifyRequested(GET, ITEMS_PATH, 2);
    }

    @Test
    void fetch_shouldRefuseRequest_whenPathNamesOtherHost() {
        final Throwable thrown = catchThrowable(() -> GitHubGenericRequest.get("//other.example/items"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        HTTP.verifyRequested(GET, ITEMS_PATH, 0);
    }

    private static GitHubGenericApi api(final GitHubClient client) {
        return new GitHubGenericApi(GitHubCaller.of(client));
    }

    private static GitHubClient client() {
        final RetryConfiguration retry = RetryConfiguration.builder()
                .rateLimitJitter(Duration.ZERO)
                .clockSkewMargin(Duration.ZERO)
                .minRateLimitWait(Duration.ZERO)
                .build();
        final GitHubClientConfiguration configuration = GitHubClientConfiguration.builder()
                .configuration(TestClients.configuration(URI.create(HTTP.baseUri() + "/")))
                .retry(retry)
                .build();
        return GitHubClients.createTokenClient(TestClients.auth(), TestClients.USER_AGENT, configuration);
    }

    private static StubHttpServer.StubResponse jsonOk(final String json) {
        return status(OK).header("Content-Type", "application/json").body(bytes(json));
    }

    private static byte[] bytes(final String text) {
        return text.getBytes(StandardCharsets.UTF_8);
    }
}
