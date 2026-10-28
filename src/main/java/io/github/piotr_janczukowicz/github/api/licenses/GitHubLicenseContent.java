package io.github.piotr_janczukowicz.github.api.licenses;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * License content.
 *
 * @param size Size
 * @param name Name
 * @param path Path
 * @param sha Sha
 * @param url Url
 * @param type Type
 * @param content Content
 * @param encoding Encoding
 * @param htmlUrl Html url
 * @param gitUrl Git url
 * @param downloadUrl Download url
 * @param links Links
 * @param license License
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/license-content")
@RecordBuilder
public record GitHubLicenseContent(
        int size,
        String name,
        String path,
        String sha,
        String url,
        String type,
        String content,
        String encoding,
        @Nullable String htmlUrl,
        @Nullable String gitUrl,
        @Nullable String downloadUrl,
        @JsonProperty("_links") GitHubLicenseContentLinks links,
        @Nullable GitHubLicenseSimple license) {

    public GitHubLicenseContent {
        requireNonNull(name, "name");
        requireNonNull(path, "path");
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
        requireNonNull(type, "type");
        requireNonNull(content, "content");
        requireNonNull(encoding, "encoding");
        requireNonNull(links, "links");
    }
}
