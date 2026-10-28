package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A relationship between two SPDX elements.
 *
 * @param relationshipType The type of relationship between the two SPDX elements.
 * @param spdxElementId The SPDX identifier of the package that is the source of the relationship.
 * @param relatedSpdxElement The SPDX identifier of the package that is the target of the relationship.
 * @deprecated Closing down on {@code 2026-11-13} together with the synchronous SBOM export; migrate to the asynchronous
 *     flow (generate a report, then fetch it).
 */
@Deprecated(since = "2026-05-12")
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/dependency-graph-spdx-sbom/properties/sbom/properties/relationships/items")
@RecordBuilder
public record GitHubSpdxSbomRelationship(
        @JsonProperty("relationshipType") String relationshipType,
        @JsonProperty("spdxElementId") String spdxElementId,
        @JsonProperty("relatedSpdxElement") String relatedSpdxElement) {

    public GitHubSpdxSbomRelationship {
        requireNonNull(relationshipType, "relationshipType");
        requireNonNull(spdxElementId, "spdxElementId");
        requireNonNull(relatedSpdxElement, "relatedSpdxElement");
    }
}
