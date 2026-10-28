package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/** Public event of the GitHub API timeline. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/event")
@RecordBuilder
public record GitHubEvent(
        String id,
        @Nullable String type,
        GitHubActor actor,
        GitHubEventRepo repo,
        @Nullable GitHubActor org,
        GitHubEventPayload payload,
        @JsonProperty("public") Boolean isPublic,
        @Nullable Instant createdAt) {
    public GitHubEvent {
        requireNonNull(id, "id");
        requireNonNull(actor, "actor");
        requireNonNull(repo, "repo");
        requireNonNull(payload, "payload");
        requireNonNull(isPublic, "isPublic");
    }
}
