package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import org.jspecify.annotations.Nullable;

/**
 * The GitHub Pages deployment status.
 *
 * @param id The ID of the GitHub Pages deployment. This is the Git SHA of the deployed commit.
 * @param statusUrl The URI to monitor GitHub Pages deployment status.
 * @param pageUrl The URI to the deployed GitHub Pages.
 * @param previewUrl The URI to the deployed GitHub Pages preview.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/page-deployment")
public record GitHubPagesDeployment(
        @CatalogSimpleXor("integer|string") String id,
        String statusUrl,
        String pageUrl,
        @Nullable String previewUrl) {

    public GitHubPagesDeployment {
        requireNonNull(id, "id");
        requireNonNull(statusUrl, "statusUrl");
        requireNonNull(pageUrl, "pageUrl");
    }
}
