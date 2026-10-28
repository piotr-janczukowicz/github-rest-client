package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Pages Source Hash
 *
 * @param branch Branch
 * @param path Path
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/pages-source-hash")
public record GitHubPagesSource(String branch, String path) {

    public GitHubPagesSource {
        requireNonNull(branch, "branch");
        requireNonNull(path, "path");
    }
}
