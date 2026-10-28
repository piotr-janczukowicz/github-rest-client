package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * An external reference of an SPDX package.
 *
 * @param referenceCategory The category of reference to an external resource this reference refers to.
 * @param referenceLocator A locator for the particular external resource this reference refers to.
 * @param referenceType The category of reference to an external resource this reference refers to.
 * @deprecated Closing down on {@code 2026-11-13} together with the synchronous SBOM export; migrate to the asynchronous
 *     flow (generate a report, then fetch it).
 */
@Deprecated(since = "2026-05-12")
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema(
        "components/schemas/dependency-graph-spdx-sbom/properties/sbom/properties/packages/items/properties/externalRefs/items")
@RecordBuilder
public record GitHubSpdxSbomExternalRef(
        @JsonProperty("referenceCategory") String referenceCategory,
        @JsonProperty("referenceLocator") String referenceLocator,
        @JsonProperty("referenceType") String referenceType) {

    public GitHubSpdxSbomExternalRef {
        requireNonNull(referenceCategory, "referenceCategory");
        requireNonNull(referenceLocator, "referenceLocator");
        requireNonNull(referenceType, "referenceType");
    }
}
