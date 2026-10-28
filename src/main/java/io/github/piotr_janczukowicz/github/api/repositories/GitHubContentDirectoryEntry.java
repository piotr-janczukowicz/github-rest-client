package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Content directory entry.
 *
 * @param type Type
 * @param size Size
 * @param name Name
 * @param path Path
 * @param content Content
 * @param sha Sha
 * @param url Url
 * @param gitUrl Git url
 * @param htmlUrl Html url
 * @param downloadUrl Download url
 * @param links Links
 */
@CatalogSchema("components/schemas/content-directory/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubContentDirectoryEntry(
        GitHubContentDirectoryEntryType type,
        int size,
        String name,
        String path,
        @Nullable String content,
        String sha,
        String url,
        @Nullable String gitUrl,
        @Nullable String htmlUrl,
        @Nullable String downloadUrl,

        @JsonProperty("_links") GitHubContentLinks links) {
    public GitHubContentDirectoryEntry {
        requireNonNull(type, "type");
        requireNonNull(name, "name");
        requireNonNull(path, "path");
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
        requireNonNull(links, "links");
    }
}
