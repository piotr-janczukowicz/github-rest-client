package io.github.piotr_janczukowicz.github.api.releases;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Release asset.
 *
 * @param url Url
 * @param browserDownloadUrl Browser download url
 * @param id Id
 * @param nodeId Node id
 * @param name Name
 * @param label Label
 * @param state State
 * @param contentType Content type
 * @param size Size
 * @param digest Digest
 * @param downloadCount Download count
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param uploader Uploader
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/release-asset")
public record GitHubReleaseAsset(
        String url,
        String browserDownloadUrl,
        long id,
        String nodeId,
        String name,
        @Nullable String label,
        GitHubReleaseAssetState state,
        String contentType,
        long size,
        @Nullable String digest,
        long downloadCount,
        Instant createdAt,
        Instant updatedAt,
        @Nullable GitHubSimpleUser uploader) {

    public GitHubReleaseAsset {
        requireNonNull(url, "url");
        requireNonNull(browserDownloadUrl, "browserDownloadUrl");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(name, "name");
        requireNonNull(state, "state");
        requireNonNull(contentType, "contentType");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
