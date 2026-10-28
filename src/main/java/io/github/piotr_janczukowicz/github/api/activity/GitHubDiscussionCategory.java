package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/** Discussion category. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/discussion/properties/category")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubDiscussionCategory(
        Instant createdAt,
        String description,
        String emoji,
        long id,
        @JsonProperty("is_answerable") boolean answerable,
        String name,
        @Nullable String nodeId,
        long repositoryId,
        String slug,
        String updatedAt) {
    public GitHubDiscussionCategory {
        requireNonNull(createdAt, "createdAt");
        requireNonNull(description, "description");
        requireNonNull(emoji, "emoji");
        requireNonNull(name, "name");
        requireNonNull(slug, "slug");
        requireNonNull(updatedAt, "updatedAt");
    }
}
