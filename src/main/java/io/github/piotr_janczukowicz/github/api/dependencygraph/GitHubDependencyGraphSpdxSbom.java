package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A schema for the SPDX JSON format returned by the Dependency Graph.
 *
 * @param sbom The SPDX document.
 * @deprecated Closing down on {@code 2026-11-13} together with the synchronous SBOM export; migrate to the asynchronous
 *     flow (generate a report, then fetch it).
 */
@Deprecated(since = "2026-05-12")
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/dependency-graph-spdx-sbom")
@RecordBuilder
public record GitHubDependencyGraphSpdxSbom(GitHubSpdxSbom sbom) {

    public GitHubDependencyGraphSpdxSbom {
        requireNonNull(sbom, "sbom");
    }
}
