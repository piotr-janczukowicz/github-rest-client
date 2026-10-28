package io.github.piotr_janczukowicz.github.api.markdown;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubMarkdownApiTest {

    private static final String HTML = "<p>Hello <strong>world</strong></p>";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubMarkdownApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void render_shouldPostJsonAndReturnHtml_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), HTML));
        final GitHubRenderMarkdownPayload payload = MarkdownCatalogFixtures.renderMarkdownPayload();

        final String html = api().render(payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(html).isEqualTo(HTML);
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().toString()).endsWith("/markdown");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("Hello");
        });
    }

    @Test
    void render_shouldPostModeAndContext_whenSet() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), HTML));
        final GitHubRenderMarkdownPayload payload = GitHubRenderMarkdownPayloadBuilder.builder(
                        MarkdownCatalogFixtures.renderMarkdownPayload())
                .mode(GitHubMarkdownMode.GFM)
                .context("owner/repo")
                .build();

        api().render(payload);
        final HttpRequest request = captureSingleRequest();
        final String body =
                new String(request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8);

        assertSoftly(softly -> {
            softly.assertThat(body).contains("\"mode\":\"gfm\"");
            softly.assertThat(body).contains("\"context\":\"owner/repo\"");
        });
    }

    @Test
    void render_shouldThrow_whenPayloadNull() {
        final GitHubMarkdownApi markdownApi = api();

        final Throwable thrown = catchThrowable(() -> markdownApi.render(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void render_shouldThrow_whenPayloadTextNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubRenderMarkdownPayloadBuilder.builder().text(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("text");
    }

    @Test
    void renderRaw_shouldPostPlainText_whenTextPlain() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), HTML));

        final String html = api().renderRaw("Hello **world**", GitHubMarkdownRawContentType.TEXT_PLAIN);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(html).isEqualTo(HTML);
            softly.assertThat(request.uri().toString()).endsWith("/markdown/raw");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("text/plain");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("Hello **world**");
        });
    }

    @Test
    void renderRaw_shouldPostMarkdownMediaType_whenTextXMarkdown() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), HTML));

        api().renderRaw("x", GitHubMarkdownRawContentType.TEXT_X_MARKDOWN);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.headers().get(CONTENT_TYPE)).contains("text/x-markdown");
    }

    @Test
    void renderRaw_shouldThrow_whenTextNull() {
        final GitHubMarkdownApi markdownApi = api();

        final Throwable thrown =
                catchThrowable(() -> markdownApi.renderRaw(null, GitHubMarkdownRawContentType.TEXT_PLAIN));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("text");
    }

    @Test
    void renderRaw_shouldThrow_whenContentTypeNull() {
        final GitHubMarkdownApi markdownApi = api();

        final Throwable thrown = catchThrowable(() -> markdownApi.renderRaw("x", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("contentType");
    }

    @Test
    void render_shouldThrowApiException_whenResponseIsNotModified() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_MODIFIED.code(), null));
        final GitHubRenderMarkdownPayload payload = MarkdownCatalogFixtures.renderMarkdownPayload();
        final GitHubMarkdownApi markdownApi = api();

        final Throwable thrown = catchThrowable(() -> markdownApi.render(payload));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void render_shouldReturnEmpty_whenOkBodyIsAbsent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), null));
        final GitHubRenderMarkdownPayload payload = MarkdownCatalogFixtures.renderMarkdownPayload();

        assertThat(api().render(payload)).isEmpty();
    }

    private GitHubMarkdownApi api() {
        return new GitHubMarkdownApi(TestClients.caller(transport));
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
