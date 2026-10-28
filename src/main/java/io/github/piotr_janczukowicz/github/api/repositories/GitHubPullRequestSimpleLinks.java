package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Pull request simple links.
 *
 * @param comments Comments
 * @param commits Commits
 * @param statuses Statuses
 * @param html Html
 * @param issue Issue
 * @param reviewComments Review comments
 * @param reviewComment Review comment
 * @param selfLink Self
 */
@CatalogSchema("components/schemas/pull-request-simple/properties/_links")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestSimpleLinks(
        GitHubLink comments,
        GitHubLink commits,
        GitHubLink statuses,
        GitHubLink html,
        GitHubLink issue,
        GitHubLink reviewComments,
        GitHubLink reviewComment,
        @JsonProperty("self") GitHubLink selfLink) {
    public GitHubPullRequestSimpleLinks {
        requireNonNull(comments, "comments");
        requireNonNull(commits, "commits");
        requireNonNull(statuses, "statuses");
        requireNonNull(html, "html");
        requireNonNull(issue, "issue");
        requireNonNull(reviewComments, "reviewComments");
        requireNonNull(reviewComment, "reviewComment");
        requireNonNull(selfLink, "selfLink");
    }
}
