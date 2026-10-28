package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Page Build Status
 *
 * @param url Url
 * @param status Status
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/page-build-status")
public record GitHubPageBuildStatus(String url, String status) {

    public GitHubPageBuildStatus {
        requireNonNull(url, "url");
        requireNonNull(status, "status");
    }
}
