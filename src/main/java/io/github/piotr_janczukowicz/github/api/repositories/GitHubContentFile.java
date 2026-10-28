package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import org.jspecify.annotations.Nullable;

/**
 * Content file.
 *
 * @param type Type
 * @param encoding Encoding
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
 * @param target Target
 * @param submoduleGitUrl Submodule git url
 */
@CatalogSchema("components/schemas/content-file")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubContentFile(
        GitHubContentFileType type,
        String encoding,
        int size,
        String name,
        String path,
        String content,
        String sha,
        String url,
        @Nullable String gitUrl,
        @Nullable String htmlUrl,
        @Nullable String downloadUrl,

        @JsonProperty("_links") GitHubContentLinks links,

        @Nullable String target,

        @SuppressSchemaTypeMismatch(
                "content-file dump declares submodule_git_url as a plain string; the value is a git URI (content-submodule uses format uri)")
        @Nullable String submoduleGitUrl)
        implements GitHubContentResult {

    public GitHubContentFile {
        requireNonNull(type, "type");
        requireNonNull(encoding, "encoding");
        requireNonNull(name, "name");
        requireNonNull(path, "path");
        requireNonNull(content, "content");
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
        requireNonNull(links, "links");
    }
}
