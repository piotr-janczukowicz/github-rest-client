package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Author of a commit in a push event.
 *
 * @param name author name
 * @param email author email address
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/push-event/properties/commits/items/properties/author")
@RecordBuilder
public record GitHubPushEventCommitAuthor(
        @Nullable String name, @Nullable String email) {}
