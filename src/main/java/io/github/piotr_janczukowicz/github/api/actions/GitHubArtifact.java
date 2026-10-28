package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * An artifact produced by a workflow run.
 *
 * @param id The ID of the artifact
 * @param nodeId The global node ID of the artifact
 * @param name The name of the artifact
 * @param sizeInBytes The size in bytes of the artifact
 * @param url The API URL of the artifact
 * @param archiveDownloadUrl The URL to download the artifact archive
 * @param expired Whether the artifact has expired
 * @param createdAt The creation time of the artifact
 * @param expiresAt The expiration time of the artifact
 * @param updatedAt The last update time of the artifact
 * @param digest The SHA256 digest of the artifact
 * @param workflowRun The workflow run that produced the artifact
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/artifact")
public record GitHubArtifact(
        long id,
        String nodeId,
        String name,
        int sizeInBytes,
        String url,
        String archiveDownloadUrl,
        boolean expired,
        @Nullable Instant createdAt,
        @Nullable Instant expiresAt,
        @Nullable Instant updatedAt,
        @Nullable String digest,
        @Nullable GitHubArtifactWorkflowRun workflowRun) {

    public GitHubArtifact {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(name, "name");
        requireNonNull(url, "url");
        requireNonNull(archiveDownloadUrl, "archiveDownloadUrl");
    }
}
