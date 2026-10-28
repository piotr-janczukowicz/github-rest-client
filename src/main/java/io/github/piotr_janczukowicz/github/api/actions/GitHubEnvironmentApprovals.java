package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * An entry in the reviews log for environment deployments.
 *
 * @param environments The list of environments that were approved or rejected
 * @param state Whether deployment to the environment(s) was approved, rejected or is pending
 * @param user The user who reviewed the deployment
 * @param comment The comment submitted with the deployment review
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/environment-approvals")
public record GitHubEnvironmentApprovals(
        List<GitHubEnvironmentApprovalsEnvironment> environments,
        GitHubEnvironmentApprovalState state,
        GitHubSimpleUser user,
        String comment) {

    public GitHubEnvironmentApprovals {
        requireNonNull(environments, "environments");
        requireNonNull(state, "state");
        requireNonNull(user, "user");
        requireNonNull(comment, "comment");
    }
}
