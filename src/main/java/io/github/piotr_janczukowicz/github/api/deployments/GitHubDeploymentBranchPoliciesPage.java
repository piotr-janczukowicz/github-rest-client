package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List deployment branch policies response.
 *
 * @param totalCount The number of deployment branch policies for the environment.
 * @param branchPolicies Branch policies
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/repos/list-deployment-branch-policies/200")
@RecordBuilder
public record GitHubDeploymentBranchPoliciesPage(int totalCount, List<GitHubDeploymentBranchPolicy> branchPolicies) {

    public GitHubDeploymentBranchPoliciesPage {
        requireNonNull(branchPolicies, "branchPolicies");
    }
}
