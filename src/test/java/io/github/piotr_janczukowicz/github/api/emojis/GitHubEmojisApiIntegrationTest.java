package io.github.piotr_janczukowicz.github.api.emojis;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.emojisApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEmojisApiIntegrationTest {

    private static final String EMOJIS_PATH = "/emojis";
    private static final String EMOJIS_JSON =
            "{\"+1\":\"https://example.test/1f44d.png\",\"100\":\"https://example.test/1f4af.png\"}";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void get_shouldReturnEmojis_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, EMOJIS_PATH, okJson(EMOJIS_JSON.getBytes(StandardCharsets.UTF_8)));

        final Map<String, String> emojis;
        try (final GitHubApiWireMockSupport.EmojisApiClient client = emojisApi(HTTP)) {
            emojis = client.api().get();
        }

        assertThat(emojis)
                .containsEntry("+1", "https://example.test/1f44d.png")
                .containsEntry("100", "https://example.test/1f4af.png")
                .hasSize(2);
        HTTP.verifyRequested(GET, EMOJIS_PATH);
    }

    @Test
    void get_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, EMOJIS_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.EmojisApiClient client = emojisApi(HTTP)) {
            final GitHubEmojisApi facade = client.api();

            thrown = catchThrowable(facade::get);
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, EMOJIS_PATH);
    }
}
