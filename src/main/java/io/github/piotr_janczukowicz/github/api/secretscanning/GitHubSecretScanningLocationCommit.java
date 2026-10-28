package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Represents a 'commit' secret scanning location type. This location type shows that a secret was detected inside a
 * commit to a repository.
 *
 * @param path The file path in the repository
 * @param startLine Line number at which the secret starts in the file
 * @param endLine Line number at which the secret ends in the file
 * @param startColumn The column at which the secret starts within the start line when the file is interpreted as 8BIT
 *     ASCII
 * @param endColumn The column at which the secret ends within the end line when the file is interpreted as 8BIT ASCII
 * @param blobSha SHA-1 hash ID of the associated blob
 * @param blobUrl The API URL to get the associated blob resource
 * @param commitSha SHA-1 hash ID of the associated commit
 * @param commitUrl The API URL to get the associated commit resource
 * @param htmlUrl The GitHub URL to get the associated commit resource.
 */
@CatalogSchema("components/schemas/secret-scanning-location-commit")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningLocationCommit(
        String path,

        double startLine,

        double endLine,

        double startColumn,

        double endColumn,

        String blobSha,
        String blobUrl,
        String commitSha,
        String commitUrl,
        @Nullable String htmlUrl)
        implements GitHubSecretScanningLocationDetails, GitHubSecretScanningFirstDetectedLocation {

    public GitHubSecretScanningLocationCommit {
        requireNonNull(path, "path");
        requireNonNull(blobSha, "blobSha");
        requireNonNull(blobUrl, "blobUrl");
        requireNonNull(commitSha, "commitSha");
        requireNonNull(commitUrl, "commitUrl");
    }
}
