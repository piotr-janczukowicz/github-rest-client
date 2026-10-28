package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.issues.GitHubLabel;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Discussion. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/discussion")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubDiscussion(
        @Nullable String activeLockReason,
        @Nullable String answerChosenAt,
        @Nullable GitHubDiscussionAnswerChosenBy answerChosenBy,
        @Nullable String answerHtmlUrl,
        @Nullable GitHubDiscussionAuthorAssociation authorAssociation,
        String body,
        GitHubDiscussionCategory category,
        int comments,
        Instant createdAt,
        String htmlUrl,
        long id,
        boolean locked,
        String nodeId,
        int number,
        @Nullable GitHubDiscussionReactions reactions,
        String repositoryUrl,
        GitHubDiscussionState state,
        @Nullable GitHubDiscussionStateReason stateReason,
        @Nullable String timelineUrl,
        String title,
        Instant updatedAt,
        @Nullable GitHubDiscussionUser user,
        @Nullable List<GitHubLabel> labels) {
    public GitHubDiscussion {
        requireNonNull(body, "body");
        requireNonNull(category, "category");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(repositoryUrl, "repositoryUrl");
        requireNonNull(state, "state");
        requireNonNull(title, "title");
        requireNonNull(updatedAt, "updatedAt");
    }
}
