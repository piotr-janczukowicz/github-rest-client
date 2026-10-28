package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Content symlink.
 *
 * @param type Type
 * @param target Target
 * @param size Size
 * @param name Name
 * @param path Path
 * @param sha Sha
 * @param url Url
 * @param gitUrl Git url
 * @param htmlUrl Html url
 * @param downloadUrl Download url
 * @param links Links
 */
@CatalogSchema("components/schemas/content-symlink")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubContentSymlink(
        GitHubContentSymlinkType type,
        String target,
        int size,
        String name,
        String path,
        String sha,
        String url,
        @Nullable String gitUrl,
        @Nullable String htmlUrl,
        @Nullable String downloadUrl,

        @JsonProperty("_links") GitHubContentLinks links)
        implements GitHubContentResult {

    public GitHubContentSymlink {
        requireNonNull(type, "type");
        requireNonNull(target, "target");
        requireNonNull(name, "name");
        requireNonNull(path, "path");
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
        requireNonNull(links, "links");
    }
}
