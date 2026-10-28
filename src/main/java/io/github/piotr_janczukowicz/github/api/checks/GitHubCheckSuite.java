package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestMinimal;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A suite of checks performed on the code of a given code change.
 *
 * @param id The id of the check suite.
 * @param nodeId Node id
 * @param headBranch Head branch
 * @param headSha The SHA of the head commit that is being checked.
 * @param status The phase of the lifecycle that the check suite is currently in.
 * @param conclusion Conclusion
 * @param url URL
 * @param before Before
 * @param after After
 * @param pullRequests Pull requests
 * @param app App
 * @param repository Repository
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param headCommit Head commit
 * @param latestCheckRunsCount Latest check runs count
 * @param checkRunsUrl Check runs URL
 * @param rerequestable Rerequestable
 * @param runsRerequestable Runs rerequestable
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/check-suite")
public record GitHubCheckSuite(
        long id,
        String nodeId,
        @Nullable String headBranch,
        String headSha,
        @Nullable GitHubCheckSuiteStatus status,
        @Nullable GitHubCheckSuiteConclusion conclusion,
        @Nullable String url,
        @Nullable String before,
        @Nullable String after,
        @Nullable List<GitHubPullRequestMinimal> pullRequests,
        @Nullable GitHubIntegration app,
        GitHubMinimalRepository repository,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt,
        GitHubSimpleCommit headCommit,
        int latestCheckRunsCount,
        String checkRunsUrl,
        @Nullable Boolean rerequestable,
        @Nullable Boolean runsRerequestable) {

    public GitHubCheckSuite {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(headSha, "headSha");
        requireNonNull(repository, "repository");
        requireNonNull(headCommit, "headCommit");
        requireNonNull(checkRunsUrl, "checkRunsUrl");
    }
}
