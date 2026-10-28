package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A commit in a push event. Only GitHub Enterprise Server describes the commits of a push event.
 *
 * @param sha commit SHA
 * @param message commit message
 * @param author commit author
 * @param url API URL of the commit
 * @param distinct whether the commit is distinct from the commits of other pushes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/push-event/properties/commits/items")
@RecordBuilder
public record GitHubPushEventCommit(
        @Nullable String sha,
        @Nullable String message,
        @Nullable GitHubPushEventCommitAuthor author,
        @Nullable String url,
        @Nullable Boolean distinct) {}
