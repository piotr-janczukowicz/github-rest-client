package io.github.piotr_janczukowicz.github.api.deployments;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create or update an environment.
 *
 * @param waitTimer The amount of time to delay a job after the job is initially triggered. The time (in minutes) must
 *     be an integer between 0 and 43,200 (30 days).
 * @param preventSelfReview Whether or not a user who created the job is prevented from approving their own job.
 * @param reviewers The people or teams that may review jobs that reference the environment. You can list up to six
 *     users or teams as reviewers. The reviewers must have at least read access to the repository. Only one of the
 *     required reviewers needs to approve the job for it to proceed.
 * @param deploymentBranchPolicy The type of deployment branch policy for this environment. To allow all branches to
 *     deploy, set to {@code null}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-or-update-environment/requestBody")
public record GitHubCreateOrUpdateEnvironmentPayload(
        @Nullable Integer waitTimer,
        @Nullable Boolean preventSelfReview,
        @Nullable List<GitHubCreateOrUpdateEnvironmentReviewerPayload> reviewers,
        @Nullable OptionalNull<GitHubDeploymentBranchPolicySettings> deploymentBranchPolicy) {

    public GitHubCreateOrUpdateEnvironmentPayload {
        if (reviewers != null) {
            reviewers = List.copyOf(reviewers);
        }
    }
}
