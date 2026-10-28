package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestMinimal;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A check performed on the code of a given code change.
 *
 * @param id The id of the check.
 * @param headSha The SHA of the commit that is being checked.
 * @param nodeId Node id
 * @param externalId A reference for the run on the integrator's system.
 * @param url URL
 * @param htmlUrl HTML URL
 * @param detailsUrl The URL of the integrator's site that has the full details of the check.
 * @param status The phase of the lifecycle that the check is currently in.
 * @param conclusion The final conclusion of the check.
 * @param startedAt Started at
 * @param completedAt Completed at
 * @param output Output
 * @param name The name of the check.
 * @param checkSuite Check suite
 * @param app App
 * @param pullRequests Pull requests that are open with a {@code head_sha} or {@code head_branch} that matches the
 *     check.
 * @param deployment Deployment
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/check-run")
public record GitHubCheckRun(
        long id,
        String headSha,
        String nodeId,
        @Nullable String externalId,
        String url,
        @Nullable String htmlUrl,
        @Nullable String detailsUrl,
        GitHubCheckRunStatus status,
        @Nullable GitHubCheckRunConclusion conclusion,
        @Nullable Instant startedAt,
        @Nullable Instant completedAt,
        GitHubCheckRunOutput output,
        String name,
        @Nullable GitHubCheckRunCheckSuite checkSuite,
        @Nullable GitHubIntegration app,
        List<GitHubPullRequestMinimal> pullRequests,
        @Nullable GitHubDeploymentSimple deployment) {

    public GitHubCheckRun {
        requireNonNull(headSha, "headSha");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        requireNonNull(status, "status");
        requireNonNull(output, "output");
        requireNonNull(name, "name");
        requireNonNull(pullRequests, "pullRequests");
    }
}
