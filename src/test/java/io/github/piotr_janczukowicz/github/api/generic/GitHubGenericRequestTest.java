package io.github.piotr_janczukowicz.github.api.generic;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.HttpHeaders;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.client.HttpCall;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class GitHubGenericRequestTest {

    private final HttpTransport transport = HttpTransportMocks.mock();

    @ParameterizedTest
    @ValueSource(
            strings = {
                "repos",
                "https://evil.example/repos",
                "//evil.example/repos",
                "/repos?per_page=1",
                "/repos#top",
                "/repos/../admin",
                "/repos/./x",
                "/repos/%2e%2e/admin",
                "/repos\\admin",
                "/repos%2Fadmin",
                "/repos/%5cadmin"
            })
    void constructor_shouldThrow_whenPathIsAmbiguous(final String path) {
        final Throwable thrown = catchThrowable(() -> GitHubGenericRequest.get(path));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("path");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Authorization", "authorization", "X-GitHub-Api-Version", "X-GITHUB-API-VERSION", "Host"})
    void constructor_shouldThrow_whenHeaderIsReserved(final String name) {
        final HttpHeaders headers = HttpHeaders.of(ACCEPT, "application/json", name, "x");
        final GitHubGenericRequest request = GitHubGenericRequest.get("/zen");

        final Throwable thrown = catchThrowable(() -> request.withHeaders(headers));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(name);
    }

    @Test
    void constructor_shouldThrow_whenBothJsonBodyAndBodySet() {
        final GitHubGenericRequest request = GitHubGenericRequest.post("/x").withJsonBody(Map.of("a", 1));
        final HttpBody body = HttpBody.of(new byte[] {1});

        final Throwable thrown = catchThrowable(() -> request.withBody(body));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("body");
    }

    @Test
    void constructor_shouldThrow_whenPathNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGenericRequest.get(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void get_shouldReturnGetRequestWithoutBody_whenPathGiven() {
        final GitHubGenericRequest request = GitHubGenericRequest.get("/zen");

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.path()).isEqualTo("/zen");
            softly.assertThat(request.pathVariables()).isEmpty();
            softly.assertThat(request.query()).isEmpty();
            softly.assertThat(request.headers().isEmpty()).isTrue();
            softly.assertThat(request.jsonBody()).isNull();
            softly.assertThat(request.body()).isNull();
        });
    }

    @Test
    void constructor_shouldCopyQueryValues_whenCallerMutatesListLater() {
        final List<String> values = new ArrayList<>(List.of("1"));
        final GitHubGenericRequest request = GitHubGenericRequest.get("/x").withQuery(Map.of("k", values));

        values.add("2");

        assertThat(request.query()).containsEntry("k", List.of("1"));
    }

    @Test
    void constructor_shouldKeepQueryOrder_whenQueryIsOrdered() {
        final Map<String, List<String>> ordered = new LinkedHashMap<>();
        ordered.put("a", List.of("1"));
        ordered.put("b", List.of("1"));
        ordered.put("z", List.of("1"));
        ordered.put("m", List.of("1"));

        final GitHubGenericRequest request = GitHubGenericRequest.get("/x").withQuery(ordered);

        assertThat(request.query().keySet()).containsExactly("a", "b", "z", "m");
    }

    @Test
    void constructor_shouldThrow_whenContentTypeHeaderSetWithJsonBody() {
        final GitHubGenericRequest request = GitHubGenericRequest.post("/x").withJsonBody(Map.of("a", 1));
        final HttpHeaders headers = HttpHeaders.of(CONTENT_TYPE, "application/vnd.github+json");

        final Throwable thrown = catchThrowable(() -> request.withHeaders(headers));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Content-Type");
    }

    @Test
    void constructor_shouldThrow_whenJsonBodySetWithContentTypeHeader() {
        final GitHubGenericRequest request = GitHubGenericRequest.post("/x")
                .withHeaders(HttpHeaders.of(CONTENT_TYPE, "application/vnd.github+json"));

        final Throwable thrown = catchThrowable(() -> request.withJsonBody(Map.of("a", 1)));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Content-Type");
    }

    @Test
    void put_shouldReturnPutRequest_whenPathGiven() {
        final GitHubGenericRequest request = GitHubGenericRequest.put("/x");

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void patch_shouldReturnPatchRequest_whenPathGiven() {
        final GitHubGenericRequest request = GitHubGenericRequest.patch("/x");

        assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
    }

    @Test
    void delete_shouldReturnDeleteRequest_whenPathGiven() {
        final GitHubGenericRequest request = GitHubGenericRequest.delete("/x");

        assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void toCall_shouldSetRawBody_whenBodySet() {
        final GitHubCaller caller = TestClients.caller(transport);
        final HttpBody body = HttpBody.of(new byte[] {1, 2});
        final GitHubGenericRequest request = GitHubGenericRequest.put("/x").withBody(body);

        final HttpCall call = request.toCall(caller);

        assertThat(call.body()).isEqualTo(body);
    }

    @Test
    void toCall_shouldThrow_whenCallerNull() {
        final GitHubGenericRequest request = GitHubGenericRequest.get("/x");

        final Throwable thrown = catchThrowable(() -> request.toCall(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void toCall_shouldEncodeVariableAsOneSegment_whenValueContainsSlashDotsAndQuestionMark() {
        final GitHubCaller caller = TestClients.caller(transport);
        final GitHubGenericRequest request =
                GitHubGenericRequest.get("/repos/{owner}/{repo}").withPathVariables(List.of("a/b", "..?x"));

        final HttpCall call = request.toCall(caller);
        final String path = call.requirePath();

        assertSoftly(softly -> {
            softly.assertThat(path).startsWith("/repos/");
            softly.assertThat(path.substring("/repos/".length()).split("/")).hasSize(2);
            softly.assertThat(path).doesNotContain("?").doesNotContain("/../");
        });
    }

    @Test
    void toCall_shouldKeepQueryAndAllowedHeaders_whenSet() {
        final GitHubCaller caller = TestClients.caller(transport);
        final GitHubGenericRequest request = GitHubGenericRequest.get("/zen")
                .withQuery(Map.of("q", List.of("a", "b")))
                .withHeaders(HttpHeaders.of(ACCEPT, "text/plain", "X-Custom", "1"));

        final HttpCall call = request.toCall(caller);

        assertSoftly(softly -> {
            softly.assertThat(call.query()).containsEntry("q", List.of("a", "b"));
            softly.assertThat(call.headers().get(ACCEPT)).contains("text/plain");
            softly.assertThat(call.headers().get("X-Custom")).contains("1");
        });
    }

    @Test
    void toCall_shouldEncodeJsonBody_whenJsonBodySet() {
        final GitHubCaller caller = TestClients.caller(transport);
        final GitHubGenericRequest request = GitHubGenericRequest.post("/x").withJsonBody(Map.of("a", 1));

        final HttpCall call = request.toCall(caller);

        assertThat(call.body()).isNotEqualTo(HttpBody.absent());
    }
}
