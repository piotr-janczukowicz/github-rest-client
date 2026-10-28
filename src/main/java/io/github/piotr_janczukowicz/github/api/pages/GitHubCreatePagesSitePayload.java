package io.github.piotr_janczukowicz.github.api.pages;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The source branch and directory used to publish your Pages site.
 *
 * @param buildType The process in which the Page will be built. Possible values are {@code "legacy"} and
 *     {@code "workflow"}.
 * @param source The source branch and directory used to publish your Pages site.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-pages-site/requestBody")
public record GitHubCreatePagesSitePayload(
        @Nullable GitHubPagesBuildType buildType, @Nullable GitHubCreatePagesSiteSource source) {

    public GitHubCreatePagesSitePayload {
        if (source == null && buildType == null) {
            throw new IllegalArgumentException("at least one of source or buildType is required");
        }
    }
}
