package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request body for {@code PATCH /repos/{owner}/{repo}/check-runs/{check_run_id}}.
 *
 * @param name The name of the check.
 * @param detailsUrl The URL of the integrator's site that has the full details of the check.
 * @param externalId A reference for the run on the integrator's system.
 * @param startedAt This is a timestamp in ISO 8601 format.
 * @param status The current status of the check run.
 * @param conclusion Required if you provide {@code completed_at} or a {@code status} of {@code completed}.
 * @param completedAt The time the check completed.
 * @param output Check runs can accept a variety of data in the {@code output} object.
 * @param actions Possible further actions the integrator can perform, which a user may trigger.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/checks/update/requestBody")
public record GitHubUpdateCheckRunPayload(
        @Nullable String name,
        @Nullable String detailsUrl,
        @Nullable String externalId,
        @Nullable Instant startedAt,
        @Nullable GitHubUpdateCheckRunStatus status,
        @Nullable GitHubUpdateCheckRunConclusion conclusion,
        @Nullable Instant completedAt,
        @Nullable GitHubUpdateCheckRunOutput output,
        @Nullable List<GitHubUpdateCheckRunAction> actions) {

    public GitHubUpdateCheckRunPayload {
        if (status == GitHubUpdateCheckRunStatus.COMPLETED && conclusion == null) {
            throw new IllegalArgumentException("conclusion is required when status is completed");
        }
        if (actions != null) {
            actions = List.copyOf(requireSizeAtMost(actions, 3, "actions"));
        }
    }
}
