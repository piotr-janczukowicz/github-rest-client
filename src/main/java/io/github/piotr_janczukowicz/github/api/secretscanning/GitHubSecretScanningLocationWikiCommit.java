package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Represents a 'wiki_commit' secret scanning location type. This location type shows that a secret was detected inside
 * a commit to a repository wiki.
 *
 * @param path The file path of the wiki page
 * @param startLine Line number at which the secret starts in the file
 * @param endLine Line number at which the secret ends in the file
 * @param startColumn The column at which the secret starts within the start line when the file is interpreted as 8-bit
 *     ASCII.
 * @param endColumn The column at which the secret ends within the end line when the file is interpreted as 8-bit ASCII.
 * @param blobSha SHA-1 hash ID of the associated blob
 * @param pageUrl The GitHub URL to get the associated wiki page
 * @param commitSha SHA-1 hash ID of the associated commit
 * @param commitUrl The GitHub URL to get the associated wiki commit
 */
@CatalogSchema("components/schemas/secret-scanning-location-wiki-commit")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningLocationWikiCommit(
        String path,

        double startLine,

        double endLine,

        double startColumn,

        double endColumn,

        String blobSha,
        String pageUrl,
        String commitSha,
        String commitUrl)
        implements GitHubSecretScanningLocationDetails, GitHubSecretScanningFirstDetectedLocation {

    public GitHubSecretScanningLocationWikiCommit {
        requireNonNull(path, "path");
        requireNonNull(blobSha, "blobSha");
        requireNonNull(pageUrl, "pageUrl");
        requireNonNull(commitSha, "commitSha");
        requireNonNull(commitUrl, "commitUrl");
    }
}
