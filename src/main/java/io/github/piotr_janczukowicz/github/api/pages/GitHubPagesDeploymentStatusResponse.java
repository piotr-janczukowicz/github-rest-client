package io.github.piotr_janczukowicz.github.api.pages;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHub Pages deployment status
 *
 * @param status The current status of the deployment.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/pages-deployment-status")
public record GitHubPagesDeploymentStatusResponse(@Nullable GitHubPagesDeploymentStatus status) {}
