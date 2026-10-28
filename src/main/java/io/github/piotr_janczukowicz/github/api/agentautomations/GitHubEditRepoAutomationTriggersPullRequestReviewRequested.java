package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Configuration of the pull request review requested trigger.
 *
 * @param query A GitHub issue or pull request search query.
 * @param paths Gitignore-style changed-file path globs.
 * @param writeScope Override the Automation {@code write_scope} for runs started by this trigger. If omitted, the
 *     automation's {@code write_scope} is used. Organization or repository policy may restrict the effective scope to
 *     {@code trigger}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_review_requested")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubEditRepoAutomationTriggersPullRequestReviewRequested(
        @Nullable String query,
        @Nullable List<String> paths,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestReviewRequestedWriteScope writeScope) {
    public GitHubEditRepoAutomationTriggersPullRequestReviewRequested {
        requireMinLengthIfPresent(query, 1, "query");
        if (paths != null) {
            paths = List.copyOf(requireNonEmpty(paths, "paths"));
            for (final String path : paths) {
                requireMinLength(path, 1, "paths");
            }
        }
    }
}
