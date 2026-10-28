package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Creation information of an SPDX document.
 *
 * @param created The date and time the SPDX document was created.
 * @param creators The tools that were used to generate the SPDX document.
 * @deprecated Closing down on {@code 2026-11-13} together with the synchronous SBOM export; migrate to the asynchronous
 *     flow (generate a report, then fetch it).
 */
@Deprecated(since = "2026-05-12")
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/dependency-graph-spdx-sbom/properties/sbom/properties/creationInfo")
@RecordBuilder
public record GitHubSpdxSbomCreationInfo(
        @JsonProperty("created") String created,
        @JsonProperty("creators") List<String> creators) {

    public GitHubSpdxSbomCreationInfo {
        requireNonNull(created, "created");
        requireNonNull(creators, "creators");
    }
}
