package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * File commit content.
 *
 * @param name Name
 * @param path Path
 * @param sha Sha
 * @param size Size
 * @param url Url
 * @param htmlUrl Html url
 * @param gitUrl Git url
 * @param downloadUrl Download url
 * @param type Type
 * @param links Links
 */
@CatalogSchema("components/schemas/file-commit/properties/content")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubFileCommitContent(
        @Nullable String name,
        @Nullable String path,
        @Nullable String sha,
        @Nullable Integer size,
        @Nullable String url,
        @Nullable String htmlUrl,
        @Nullable String gitUrl,
        @Nullable String downloadUrl,
        @Nullable String type,

        @JsonProperty("_links") @Nullable GitHubFileCommitContentLinks links) {}
