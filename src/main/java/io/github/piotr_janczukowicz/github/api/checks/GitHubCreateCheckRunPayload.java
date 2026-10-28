package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request body for {@code POST /repos/{owner}/{repo}/check-runs}.
 *
 * @param name The name of the check.
 * @param headSha The SHA of the commit.
 * @param detailsUrl The URL of the integrator's site that has the full details of the check.
 * @param externalId A reference for the run on the integrator's system.
 * @param status The current status of the check run.
 * @param startedAt The time that the check run began.
 * @param conclusion Required if you provide {@code completed_at} or a {@code status} of {@code completed}.
 * @param completedAt The time the check completed.
 * @param output Check runs can accept a variety of data in the {@code output} object, including a {@code title} and
 *     {@code summary}.
 * @param actions Displays a button on GitHub that can be clicked to alert your app to do additional tasks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/checks/create/requestBody")
public record GitHubCreateCheckRunPayload(
        String name,
        String headSha,
        @Nullable String detailsUrl,
        @Nullable String externalId,
        @Nullable GitHubCreateCheckRunStatus status,
        @Nullable Instant startedAt,
        @Nullable GitHubCreateCheckRunConclusion conclusion,
        @Nullable Instant completedAt,
        @Nullable GitHubCreateCheckRunOutput output,
        @Nullable List<GitHubCreateCheckRunAction> actions) {

    public GitHubCreateCheckRunPayload {
        requireNonNull(name, "name");
        requireNonNull(headSha, "headSha");
        if (status == GitHubCreateCheckRunStatus.COMPLETED && conclusion == null) {
            throw new IllegalArgumentException("conclusion is required when status is completed");
        }
        if (actions != null) {
            actions = List.copyOf(requireSizeAtMost(actions, 3, "actions"));
        }
    }
}
