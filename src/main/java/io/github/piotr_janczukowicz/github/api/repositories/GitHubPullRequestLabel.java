package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Pull request label.
 *
 * @param id Id
 * @param nodeId Node id
 * @param url Url
 * @param name Name
 * @param description Description
 * @param color Color
 * @param defaultLabel Default label
 * @param archivedAt Timestamp indicating when the label was archived, or {@code null} if it has not been archived
 * @param archivedBy The user who archived the label, or {@code null} if it has not been archived
 */
@CatalogSchema("components/schemas/pull-request-simple/properties/labels/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestLabel(
        long id,
        String nodeId,
        String url,
        String name,
        String description,
        String color,
        @JsonProperty("default") boolean defaultLabel,
        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable Instant archivedAt,
        @Nullable GitHubSimpleUser archivedBy) {
    public GitHubPullRequestLabel {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        requireNonNull(name, "name");
        requireNonNull(description, "description");
        requireNonNull(color, "color");
    }
}
