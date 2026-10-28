package io.github.piotr_janczukowicz.github.api.markdown;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpStatus.OK;
import static io.github.piotr_janczukowicz.test.http.StubHttpServer.status;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.markdownApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubMarkdownApiIntegrationTest {

    private static final String HTML = "<p>Hello <strong>world</strong></p>";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void render_shouldReturnHtml_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                "/markdown",
                status(OK).header(CONTENT_TYPE, "text/html").body(HTML.getBytes(StandardCharsets.UTF_8)));

        final String html;
        try (final GitHubApiWireMockSupport.MarkdownApiClient markdown = markdownApi(HTTP)) {
            html = markdown.api().render(MarkdownCatalogFixtures.renderMarkdownPayload());
        }

        assertThat(html).isEqualTo(HTML);
        HTTP.verifyHeader(POST, "/markdown", CONTENT_TYPE, "application/json");
    }

    @Test
    void renderRaw_shouldReturnHtml_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                "/markdown/raw",
                status(OK).header(CONTENT_TYPE, "text/html").body(HTML.getBytes(StandardCharsets.UTF_8)));

        final String html;
        try (final GitHubApiWireMockSupport.MarkdownApiClient markdown = markdownApi(HTTP)) {
            html = markdown.api().renderRaw("Hello **world**", GitHubMarkdownRawContentType.TEXT_PLAIN);
        }

        assertThat(html).isEqualTo(HTML);
        HTTP.verifyHeader(POST, "/markdown/raw", CONTENT_TYPE, "text/plain");
    }
}
