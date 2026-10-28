package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Blob.
 *
 * @param content Content
 * @param encoding Encoding
 * @param url Url
 * @param sha Sha
 * @param size Size
 * @param nodeId Node id
 * @param highlightedContent Highlighted content
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/blob")
public record GitHubBlob(
        String content,
        String encoding,
        String url,
        String sha,
        @Nullable Integer size,
        String nodeId,
        @Nullable String highlightedContent) {

    public GitHubBlob {
        requireNonNull(content, "content");
        requireNonNull(encoding, "encoding");
        requireNonNull(url, "url");
        requireNonNull(sha, "sha");
        requireNonNull(nodeId, "nodeId");
    }
}
