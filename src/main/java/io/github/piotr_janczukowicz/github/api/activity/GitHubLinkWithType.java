package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Hypermedia link with type.
 *
 * @param href The URL
 * @param type The link type
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/link-with-type")
@RecordBuilder
public record GitHubLinkWithType(String href, String type) {

    public GitHubLinkWithType {
        requireNonNull(href, "href");
        requireNonNull(type, "type");
    }
}
