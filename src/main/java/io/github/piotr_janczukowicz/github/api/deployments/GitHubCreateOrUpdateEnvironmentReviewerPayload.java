package io.github.piotr_janczukowicz.github.api.deployments;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Environment reviewer on create or update.
 *
 * @param type Type
 * @param id The id of the user or team who can review the deployment
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-or-update-environment/requestBody/properties/reviewers/items")
public record GitHubCreateOrUpdateEnvironmentReviewerPayload(
        @Nullable GitHubDeploymentReviewerType type,
        @Nullable Long id) {}
