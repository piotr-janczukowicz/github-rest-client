package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Approval policy for fork pull request workflows.
 *
 * @param approvalPolicy The policy that controls when fork PR workflows require approval from a maintainer
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-fork-pr-contributor-approval")
public record GitHubActionsForkPrContributorApproval(GitHubForkPrContributorApprovalPolicy approvalPolicy) {

    public GitHubActionsForkPrContributorApproval {
        requireNonNull(approvalPolicy, "approvalPolicy");
    }
}
