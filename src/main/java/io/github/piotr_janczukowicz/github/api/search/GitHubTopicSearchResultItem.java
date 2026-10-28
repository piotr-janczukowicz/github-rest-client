package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Topic Search Result Item.
 *
 * @param name Topic name
 * @param displayName Display name
 * @param shortDescription Short description
 * @param description Description
 * @param createdBy Created by
 * @param released Released
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param featured Featured
 * @param curated Curated
 * @param score Score
 * @param repositoryCount Repository count
 * @param logoUrl Logo url
 * @param textMatches Text matches
 * @param related Related topics
 * @param aliases Aliased topics
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/topic-search-result-item")
public record GitHubTopicSearchResultItem(
        String name,
        @Nullable String displayName,
        @Nullable String shortDescription,
        @Nullable String description,
        @Nullable String createdBy,
        @Nullable String released,
        Instant createdAt,
        Instant updatedAt,
        boolean featured,
        boolean curated,

        double score,

        @Nullable Long repositoryCount,
        @Nullable String logoUrl,
        @Nullable List<GitHubSearchTextMatchItem> textMatches,
        @Nullable List<RelatedTopic> related,
        @Nullable List<RelatedTopic> aliases) {

    public GitHubTopicSearchResultItem {
        requireNonNull(name, "name");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }

    /**
     * Wrapper for a related or aliased topic entry.
     *
     * @param topicRelation Related topic metadata
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    @CatalogSchema({
        "components/schemas/topic-search-result-item/properties/related/items",
        "components/schemas/topic-search-result-item/properties/aliases/items"
    })
    public record RelatedTopic(@Nullable GitHubTopicRelation topicRelation) {}
}
