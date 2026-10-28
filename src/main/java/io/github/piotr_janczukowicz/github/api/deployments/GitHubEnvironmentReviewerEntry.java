package io.github.piotr_janczukowicz.github.api.deployments;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Environment protection rule reviewer entry.
 *
 * @param type Type
 * @param reviewer Reviewer
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/environment/properties/protection_rules/items/anyOf/1/properties/reviewers/items")
@RecordBuilder
public record GitHubEnvironmentReviewerEntry(
        @Nullable GitHubDeploymentReviewerType type,
        @Nullable GitHubEnvironmentReviewer reviewer) {}
