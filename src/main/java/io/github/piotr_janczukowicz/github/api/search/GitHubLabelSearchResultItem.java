package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Label Search Result Item.
 *
 * @param id Label id
 * @param nodeId Node id
 * @param url Url
 * @param name Name
 * @param color Color
 * @param isDefault Default
 * @param description Description
 * @param archivedAt Timestamp indicating when the label was archived, or {@code null} if it has not been archived
 * @param archivedBy The user who archived the label, or {@code null} if it has not been archived
 * @param score Score
 * @param textMatches Text matches
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/label-search-result-item")
public record GitHubLabelSearchResultItem(
        long id,
        String nodeId,
        String url,
        String name,
        String color,
        @JsonProperty("default") boolean isDefault,
        @Nullable String description,
        @Nullable Instant archivedAt,
        @Nullable GitHubSimpleUser archivedBy,

        double score,

        @Nullable List<GitHubSearchTextMatchItem> textMatches) {

    public GitHubLabelSearchResultItem {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        requireNonNull(name, "name");
        requireNonNull(color, "color");
    }
}
