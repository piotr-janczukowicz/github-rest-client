package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Agent-proposed change to an issue.
 *
 * @param id suggestion id
 * @param issueId issue id
 * @param action proposed change kind
 * @param state lifecycle state
 * @param targetId target entity id, when applicable
 * @param targetValue proposed value, when applicable
 * @param rationale actor rationale
 * @param confidence actor confidence
 * @param actorId proposing actor id
 * @param issueEventId timeline event id when approved
 * @param resolvedBy user who approved or dismissed
 * @param createdAt created at
 * @param updatedAt updated at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/issue-suggestion")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubIssueSuggestion(
        long id,
        long issueId,
        GitHubIssueSuggestionAction action,
        GitHubIssueSuggestionState state,
        @Nullable Long targetId,
        @Nullable GitHubIssueSuggestionTargetValue targetValue,
        @Nullable String rationale,
        @Nullable GitHubIssueSuggestionConfidence confidence,
        @Nullable Long actorId,
        @Nullable Long issueEventId,
        @Nullable Long resolvedBy,
        Instant createdAt,
        Instant updatedAt) {

    public GitHubIssueSuggestion {
        requireNonNull(action, "action");
        requireNonNull(state, "state");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
