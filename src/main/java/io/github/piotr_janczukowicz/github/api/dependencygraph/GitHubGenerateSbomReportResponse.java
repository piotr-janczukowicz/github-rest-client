package io.github.piotr_janczukowicz.github.api.dependencygraph;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Response to a request to generate a software bill of materials (SBOM).
 *
 * @param sbomUrl URL to poll for the SBOM export result.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/dependency-graph/generate-sbom-report/201")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGenerateSbomReportResponse(@Nullable String sbomUrl) {}
