package io.github.piotr_janczukowicz.github.api.deployments;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Details of a deployment branch or tag policy.
 *
 * @param id The unique identifier of the branch or tag policy.
 * @param nodeId Node id
 * @param name The name pattern that branches or tags must match in order to deploy to the environment.
 * @param type Whether this rule targets a branch or tag.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/deployment-branch-policy")
@RecordBuilder
public record GitHubDeploymentBranchPolicy(
        @Nullable Long id,
        @Nullable String nodeId,
        @Nullable String name,
        @Nullable GitHubDeploymentBranchPolicyType type) {}
