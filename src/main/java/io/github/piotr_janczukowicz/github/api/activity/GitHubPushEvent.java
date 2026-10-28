package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Push event payload.
 *
 * @param repositoryId The id of the repository
 * @param pushId The id of the push
 * @param ref The git ref that was pushed
 * @param head The SHA of the head commit after the push
 * @param before The SHA of the head commit before the push
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/push-event")
@RecordBuilder
public record GitHubPushEvent(
        long repositoryId,
        long pushId,
        String ref,
        String head,
        String before,
        @Nullable Integer size,
        @Nullable Integer distinctSize,
        @Nullable List<GitHubPushEventCommit> commits)
        implements GitHubEventPayload {

    public GitHubPushEvent {
        requireNonNull(ref, "ref");
        requireNonNull(head, "head");
        requireNonNull(before, "before");
    }
}
