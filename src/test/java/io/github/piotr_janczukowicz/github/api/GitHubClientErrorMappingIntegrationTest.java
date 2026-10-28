package io.github.piotr_janczukowicz.github.api;

import static io.github.piotr_janczukowicz.github.GitHubHeaders.RATE_LIMIT_REMAINING;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpStatus.FORBIDDEN;
import static io.github.piotr_janczukowicz.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static io.github.piotr_janczukowicz.http.HttpStatus.NOT_FOUND;
import static io.github.piotr_janczukowicz.http.HttpStatus.UNAUTHORIZED;
import static io.github.piotr_janczukowicz.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static io.github.piotr_janczukowicz.test.http.StubHttpServer.status;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.client;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.exception.GitHubForbiddenException;
import io.github.piotr_janczukowicz.github.client.exception.GitHubRateLimitException;
import io.github.piotr_janczukowicz.github.client.exception.GitHubValidationException;
import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Map;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@IntegrationTest
class GitHubClientErrorMappingIntegrationTest {

    private static final String PROBE_PATH = "/probe";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @ParameterizedTest
    @EnumSource(HttpCall.class)
    void probe_shouldThrowAuthenticationException_whenUnauthorized(final HttpCall call) {
        HTTP.stub(
                call.method(),
                PROBE_PATH,
                errorJson(UNAUTHORIZED, jsonBytes(GitHubApiWireMockSupport.class, "bad-credentials.json")));

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.WireMockClient client = client(HTTP)) {
            thrown = probe(client.client(), call);
        }

        assertThat(thrown).isInstanceOf(GitHubApiException.class).hasMessage("Bad credentials");
    }

    @ParameterizedTest
    @EnumSource(HttpCall.class)
    void probe_shouldThrowAuthorizationException_whenForbiddenWithoutRateLimit(final HttpCall call) {
        HTTP.stub(
                call.method(),
                PROBE_PATH,
                errorJson(FORBIDDEN, jsonBytes(GitHubApiWireMockSupport.class, "forbidden.json")));

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.WireMockClient client = client(HTTP)) {
            thrown = probe(client.client(), call);
        }

        assertThat(thrown).isInstanceOf(GitHubForbiddenException.class).hasMessage("Forbidden");
    }

    @ParameterizedTest
    @EnumSource(HttpCall.class)
    void probe_shouldThrowRateLimitException_whenForbiddenWithZeroRemaining(final HttpCall call) {
        HTTP.stub(
                call.method(),
                PROBE_PATH,
                status(FORBIDDEN)
                        .header(CONTENT_TYPE, "application/json")
                        .header(RATE_LIMIT_REMAINING, "0")
                        .body(jsonBytes(GitHubApiWireMockSupport.class, "rate-limit.json")));

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.WireMockClient client = client(HTTP)) {
            thrown = probe(client.client(), call);
        }

        assertThat(thrown)
                .isInstanceOf(GitHubRateLimitException.class)
                .isNotInstanceOf(GitHubForbiddenException.class)
                .hasMessageStartingWith("rate limit [primary rate limit; retry at ")
                .hasMessageEndingWith(" s]");
    }

    @ParameterizedTest
    @EnumSource(HttpCall.class)
    void probe_shouldThrowNotFoundException_whenNotFound(final HttpCall call) {
        HTTP.stub(call.method(), PROBE_PATH, status(NOT_FOUND));

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.WireMockClient client = client(HTTP)) {
            thrown = probe(client.client(), call);
        }

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @ParameterizedTest
    @EnumSource(HttpCall.class)
    void probe_shouldThrowValidationException_whenUnprocessableEntity(final HttpCall call) {
        HTTP.stub(
                call.method(),
                PROBE_PATH,
                errorJson(
                        UNPROCESSABLE_ENTITY,
                        jsonBytes(GitHubApiWireMockSupport.class, "validation-failed-login.json")));

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.WireMockClient client = client(HTTP)) {
            thrown = probe(client.client(), call);
        }

        assertSoftly(softly -> {
            softly.assertThat(thrown).isInstanceOf(GitHubValidationException.class);
            softly.assertThat(((GitHubValidationException) thrown).errors()).isNotEmpty();
        });
    }

    @ParameterizedTest
    @EnumSource(HttpCall.class)
    void probe_shouldThrowApiException_whenServerError(final HttpCall call) {
        HTTP.stub(
                call.method(),
                PROBE_PATH,
                errorJson(INTERNAL_SERVER_ERROR, jsonBytes(GitHubApiWireMockSupport.class, "server-error.json")));

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.WireMockClient client = client(HTTP)) {
            thrown = probe(client.client(), call);
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubApiException.class).hasMessage("Server Error");
    }

    private static StubHttpServer.StubResponse errorJson(final HttpStatus status, final byte[] body) {
        return status(status).header(CONTENT_TYPE, "application/json").body(body);
    }

    private static Throwable probe(final GitHubClient client, final HttpCall call) {
        return catchThrowable(() -> {
            if (call == HttpCall.GET) {
                client.execute(io.github.piotr_janczukowicz.http.client.HttpCall.get(PROBE_PATH));
            } else {
                client.execute(io.github.piotr_janczukowicz.http.client.HttpCall.post(
                                PROBE_PATH,
                                HttpBody.of(GitHubJsonCodecs.defaults().encode(Map.of("ok", true))))
                        .withHeader(CONTENT_TYPE, "application/json"));
            }
        });
    }

    enum HttpCall {
        GET(HttpMethod.GET),
        POST(HttpMethod.POST);

        private final HttpMethod method;

        HttpCall(final HttpMethod method) {
            this.method = method;
        }

        HttpMethod method() {
            return method;
        }
    }
}
