package io.github.piotr_janczukowicz.github.api.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Related or aliased topic metadata returned with topic search results.
 *
 * @param id Relation id
 * @param name Related topic name
 * @param topicId Related topic id
 * @param relationType Relation type
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/topic-search-result-item/properties/related/items/properties/topic_relation",
    "components/schemas/topic-search-result-item/properties/aliases/items/properties/topic_relation"
})
public record GitHubTopicRelation(
        @Nullable Long id,
        @Nullable String name,
        @Nullable Long topicId,
        @Nullable String relationType) {}
