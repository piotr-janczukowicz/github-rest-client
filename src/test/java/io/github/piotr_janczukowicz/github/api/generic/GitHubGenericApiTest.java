package io.github.piotr_janczukowicz.github.api.generic;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubSearchEnvelope;
import io.github.piotr_janczukowicz.http.HttpHeaders;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpResponse;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.client.HttpEmptyResponseException;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubGenericApiTest {

    private static final JsonType<List<String>> STRINGS = JsonTypes.listOf(String.class);
    private static final String API_VERSION_HEADER = "X-GitHub-Api-Version";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubGenericApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void fetch_shouldDecodeBody_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[\"a\",\"b\"]"));

        final List<String> values = api().fetch(GitHubGenericRequest.get("/versions"), STRINGS);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(values).containsExactly("a", "b");
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/versions");
        });
    }

    @Test
    void fetch_shouldThrowGitHubApiException_whenStatusIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));
        final GitHubGenericApi api = api();
        final GitHubGenericRequest request = GitHubGenericRequest.get("/missing");

        final Throwable thrown = catchThrowable(() -> api.fetch(request, STRINGS));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void fetch_shouldSendJsonBodyAndContentType_whenPostWithJsonBody() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));
        final GitHubGenericRequest request = GitHubGenericRequest.post("/x").withJsonBody(Map.of("a", 1));

        api().fetch(request, STRINGS);
        final HttpRequest sent = captureSingleRequest();
        final String body = sent.mapBody(bytes -> new String(bytes, StandardCharsets.UTF_8), () -> "");

        assertSoftly(softly -> {
            softly.assertThat(sent.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(body).isEqualTo("{\"a\":1}");
            softly.assertThat(sent.headers().get(CONTENT_TYPE))
                    .hasValueSatisfying(value -> assertThat(value).contains("application/json"));
        });
    }

    @Test
    void fetch_shouldSendPinnedApiVersionHeader_whenRequestHasCustomHeaders() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));
        final GitHubGenericRequest request =
                GitHubGenericRequest.get("/x").withHeaders(HttpHeaders.of(ACCEPT, "text/plain", "X-Custom", "1"));

        api().fetch(request, STRINGS);
        final HttpRequest sent = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(sent.headers().get(API_VERSION_HEADER)).contains(GitHubClient.API_VERSION);
            softly.assertThat(sent.headers().get("X-Custom")).contains("1");
        });
    }

    @Test
    void fetch_shouldThrowHttpEmptyResponseException_whenSuccessfulResponseHasNoBody() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), ""));
        final GitHubGenericApi api = api();
        final GitHubGenericRequest request = GitHubGenericRequest.delete("/x");

        final Throwable thrown = catchThrowable(() -> api.fetch(request, STRINGS));

        assertThat(thrown).isInstanceOf(HttpEmptyResponseException.class);
    }

    @Test
    void send_shouldReturnStatus_whenResponseHasNoBody() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), ""));

        final HttpResponse sent = api().send(GitHubGenericRequest.delete("/x"));

        assertThat(sent.hasStatus(HttpStatus.NO_CONTENT)).isTrue();
    }

    @Test
    void fetchText_shouldReturnTextWithRequestedAccept_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "diff --git"));

        final Optional<String> text = api().fetchText(GitHubGenericRequest.get("/x"), "application/vnd.github.diff");
        final HttpRequest sent = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("diff --git");
            softly.assertThat(sent.headers().get(ACCEPT)).contains("application/vnd.github.diff");
        });
    }

    @Test
    void fetchText_shouldReturnEmpty_whenStatusIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{}"));

        final Optional<String> text = api().fetchText(GitHubGenericRequest.get("/x"), "text/plain");

        assertThat(text).isEmpty();
    }

    @Test
    void fetch_shouldThrow_whenRequestNull() {
        final GitHubGenericApi api = api();

        final Throwable thrown = catchThrowable(() -> api.fetch(null, STRINGS));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void fetch_shouldThrow_whenTypeNull() {
        final GitHubGenericApi api = api();
        final GitHubGenericRequest request = GitHubGenericRequest.get("/x");

        final Throwable thrown = catchThrowable(() -> api.fetch(request, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void send_shouldThrow_whenRequestNull() {
        final GitHubGenericApi api = api();

        final Throwable thrown = catchThrowable(() -> api.send(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void fetchText_shouldThrow_whenRequestNull() {
        final GitHubGenericApi api = api();

        final Throwable thrown = catchThrowable(() -> api.fetchText(null, "text/plain"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void fetchText_shouldThrow_whenMediaTypeNull() {
        final GitHubGenericApi api = api();
        final GitHubGenericRequest request = GitHubGenericRequest.get("/x");

        final Throwable thrown = catchThrowable(() -> api.fetchText(request, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("mediaType");
    }

    @Test
    void list_shouldReturnFirstPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[\"a\",\"b\"]"));

        final GitHubPage<String> page =
                api().list(GitHubGenericRequest.get("/versions"), GitHubPageRequest.byPage(), STRINGS);
        final HttpRequest sent = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly("a", "b");
            softly.assertThat(sent.uri().getQuery()).contains("page=1");
        });
    }

    @Test
    void list_shouldThrow_whenRequestIsPost() {
        final GitHubGenericApi api = api();
        final GitHubGenericRequest request = GitHubGenericRequest.post("/versions");
        final GitHubPageRequest page = GitHubPageRequest.byPage();

        final Throwable thrown = catchThrowable(() -> api.list(request, page, STRINGS));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("GET");
    }

    @Test
    void list_shouldThrow_whenRequestHasBody() {
        final GitHubGenericApi api = api();
        final GitHubGenericRequest request =
                GitHubGenericRequest.get("/versions").withJsonBody(Map.of("a", 1));
        final GitHubPageRequest page = GitHubPageRequest.byPage();

        final Throwable thrown = catchThrowable(() -> api.list(request, page, STRINGS));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void list_shouldThrow_whenPageNull() {
        final GitHubGenericApi api = api();
        final GitHubGenericRequest request = GitHubGenericRequest.get("/versions");

        final Throwable thrown = catchThrowable(() -> api.list(request, null, STRINGS));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void search_shouldMapEnvelope_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[\"a\",\"b\"]"));

        final GitHubPage<String> page = api().search(
                        GitHubGenericRequest.get("/search/things"),
                        GitHubPageRequest.byPage(),
                        STRINGS,
                        items -> new GitHubSearchEnvelope<>(items.size(), false, items, null, null));

        assertThat(page.items()).containsExactly("a", "b");
    }

    private GitHubGenericApi api() {
        return new GitHubGenericApi(TestClients.caller(transport));
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
