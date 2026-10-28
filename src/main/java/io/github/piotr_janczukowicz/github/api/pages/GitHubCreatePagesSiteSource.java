package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The source branch and directory used to publish your Pages site.
 *
 * @param branch The repository branch used to publish your site's source files.
 * @param path The repository directory that includes the source files for the Pages site. Allowed paths are {@code /}
 *     or {@code /docs}. Default: {@code /}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-pages-site/requestBody/properties/source")
public record GitHubCreatePagesSiteSource(
        String branch, @Nullable GitHubPagesSourcePath path) {

    public GitHubCreatePagesSiteSource {
        requireNonNull(branch, "branch");
    }
}
