package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Update a deployment branch policy.
 *
 * @param name The name pattern that branches must match in order to deploy to the environment.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/deployment-branch-policy-name-pattern",
    "operations/repos/update-deployment-branch-policy/requestBody"
})
public record GitHubUpdateDeploymentBranchPolicyPayload(String name) {

    public GitHubUpdateDeploymentBranchPolicyPayload {
        requireNonNull(name, "name");
    }
}
