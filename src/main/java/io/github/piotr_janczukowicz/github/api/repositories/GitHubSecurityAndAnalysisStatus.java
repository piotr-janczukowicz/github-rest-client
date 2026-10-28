package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Secret scanning.
 *
 * @param status Status
 */
@CatalogSchema("components/schemas/security-and-analysis/properties/secret_scanning")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecurityAndAnalysisStatus(@Nullable GitHubSecurityAndAnalysisEnablement status) {}
