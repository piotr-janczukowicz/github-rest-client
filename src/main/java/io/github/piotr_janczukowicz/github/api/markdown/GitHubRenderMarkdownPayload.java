package io.github.piotr_janczukowicz.github.api.markdown;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Render a Markdown document.
 *
 * @param text The Markdown text to render in HTML.
 * @param mode The rendering mode.
 * @param context The repository context to use when creating references in {@code gfm} mode. For example, setting
 *     {@code context} to {@code octo-org/octo-repo} will change the text {@code #42} into an HTML link to issue 42 in
 *     the {@code octo-org/octo-repo} repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/markdown/render/requestBody")
public record GitHubRenderMarkdownPayload(
        String text,
        @Nullable GitHubMarkdownMode mode,
        @Nullable String context) {

    public GitHubRenderMarkdownPayload {
        requireNonNull(text, "text");
    }
}
