package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Discussion event payload. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/discussion-event")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubDiscussionEvent(String action, GitHubDiscussion discussion) implements GitHubEventPayload {
    public GitHubDiscussionEvent {
        requireNonNull(action, "action");
        requireNonNull(discussion, "discussion");
    }
}
