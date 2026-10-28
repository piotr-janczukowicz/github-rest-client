package io.github.piotr_janczukowicz.github.api.markdown;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.HttpResponse;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * GitHub Markdown REST resource for API version {@code 2026-03-10}.
 *
 * <p>Endpoints that render Markdown to HTML.
 */
public final class GitHubMarkdownApi extends GitHubResourceApi {

    private static final PathTemplate MARKDOWN = PathTemplate.of("/markdown");
    private static final PathTemplate MARKDOWN_RAW = PathTemplate.of("/markdown/raw");

    /**
     * Markdown resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubMarkdownApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Render a Markdown document to HTML ({@code POST /markdown}).
     *
     * @param payload markdown text and optional rendering context
     * @return rendered HTML
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public String render(final GitHubRenderMarkdownPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);
        return html(caller.send(caller.json(post(MARKDOWN), payload)));
    }

    /**
     * Render a Markdown document to HTML ({@code POST /markdown/raw}).
     *
     * @param text raw markdown text
     * @param contentType request {@code Content-Type} for the raw body
     * @return rendered HTML
     * @throws NullPointerException if {@code text} or {@code contentType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public String renderRaw(final String text, final GitHubMarkdownRawContentType contentType) throws IOException {
        requireNonNull(text, "text");
        requireNonNull(contentType, "contentType");
        return html(caller.send(post(MARKDOWN_RAW)
                .withHeader(CONTENT_TYPE, contentType.mediaType())
                .withBody(HttpBody.of(text.getBytes(StandardCharsets.UTF_8)))));
    }

    private static String html(final HttpResponse response) {
        if (response.body().isAbsent()) {
            return "";
        }
        return new String(response.requireBodyBytes(), StandardCharsets.UTF_8);
    }
}
