package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create a deployment branch policy.
 *
 * @param name The name pattern that branches or tags must match in order to deploy to the environment.
 * @param type Whether this rule targets a branch or tag
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/deployment-branch-policy-name-pattern-with-type",
    "operations/repos/create-deployment-branch-policy/requestBody"
})
public record GitHubCreateDeploymentBranchPolicyPayload(
        String name, @Nullable GitHubDeploymentBranchPolicyType type) {

    public GitHubCreateDeploymentBranchPolicyPayload {
        requireNonNull(name, "name");
    }
}
