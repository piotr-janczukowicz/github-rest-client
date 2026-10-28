package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.deployments.GitHubDeploymentReviewerType;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A reviewer of a pending deployment.
 *
 * @param type The type of reviewer
 * @param reviewer The user or team that may approve the deployment
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/pending-deployment/properties/reviewers/items")
public record GitHubPendingDeploymentReviewerEntry(
        @Nullable GitHubDeploymentReviewerType type,
        @Nullable GitHubPendingDeploymentReviewer reviewer) {}
