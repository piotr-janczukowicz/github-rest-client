package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Diff entry.
 *
 * @param sha Sha
 * @param filename Filename
 * @param status Status
 * @param additions Additions
 * @param deletions Deletions
 * @param changes Changes
 * @param blobUrl Blob url
 * @param rawUrl Raw url
 * @param contentsUrl Contents url
 * @param patch Patch
 * @param previousFilename Previous filename
 */
@CatalogSchema("components/schemas/diff-entry")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubDiffEntry(
        @Nullable String sha,
        String filename,
        GitHubDiffEntryStatus status,
        int additions,
        int deletions,
        int changes,
        String blobUrl,
        String rawUrl,
        String contentsUrl,
        @Nullable String patch,
        @Nullable String previousFilename) {
    public GitHubDiffEntry {
        requireNonNull(filename, "filename");
        requireNonNull(status, "status");
        requireNonNull(blobUrl, "blobUrl");
        requireNonNull(rawUrl, "rawUrl");
        requireNonNull(contentsUrl, "contentsUrl");
    }
}
