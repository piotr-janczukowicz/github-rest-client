package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Details of a deployment that is waiting for protection rules to pass.
 *
 * @param environment The environment the deployment targets
 * @param waitTimer The set duration of the wait timer
 * @param waitTimerStartedAt The time that the wait timer began
 * @param currentUserCanApprove Whether the currently authenticated user can approve the deployment
 * @param reviewers The people or teams that may approve jobs that reference the environment
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/pending-deployment")
public record GitHubPendingDeployment(
        GitHubPendingDeploymentEnvironment environment,
        int waitTimer,
        @Nullable Instant waitTimerStartedAt,
        boolean currentUserCanApprove,
        List<GitHubPendingDeploymentReviewerEntry> reviewers) {

    public GitHubPendingDeployment {
        requireNonNull(environment, "environment");
        requireNonNull(reviewers, "reviewers");
    }
}
