package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Milestone. A collection of related issues and pull requests.
 *
 * @param url Url
 * @param htmlUrl Html url
 * @param labelsUrl Labels url
 * @param id Id
 * @param nodeId Node id
 * @param number The number of the milestone.
 * @param state The state of the milestone.
 * @param title The title of the milestone.
 * @param description Description
 * @param creator Creator
 * @param openIssues Open issues
 * @param closedIssues Closed issues
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param closedAt Closed at
 * @param dueOn Due on
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/milestone")
public record GitHubMilestone(
        String url,
        String htmlUrl,
        String labelsUrl,
        long id,
        String nodeId,
        int number,
        GitHubMilestoneState state,
        String title,
        @Nullable String description,
        @Nullable GitHubSimpleUser creator,
        int openIssues,
        int closedIssues,
        Instant createdAt,
        Instant updatedAt,
        @Nullable Instant closedAt,
        @Nullable Instant dueOn) {

    public GitHubMilestone {
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(labelsUrl, "labelsUrl");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(state, "state");
        requireNonNull(title, "title");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
