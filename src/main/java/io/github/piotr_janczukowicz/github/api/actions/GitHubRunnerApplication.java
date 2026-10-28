package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A runner application that can be downloaded.
 *
 * @param os The operating system of the runner application
 * @param architecture The architecture of the runner application
 * @param downloadUrl The URL to download the runner application
 * @param filename The file name of the runner application
 * @param tempDownloadToken A short lived bearer token used to download the runner, if needed
 * @param sha256Checksum The SHA256 checksum of the runner application
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/runner-application")
public record GitHubRunnerApplication(
        String os,
        String architecture,
        String downloadUrl,
        String filename,
        @Nullable String tempDownloadToken,
        @Nullable String sha256Checksum) {

    public GitHubRunnerApplication {
        requireNonNull(os, "os");
        requireNonNull(architecture, "architecture");
        requireNonNull(downloadUrl, "downloadUrl");
        requireNonNull(filename, "filename");
    }

    /**
     * Describes this record without its secrets.
     *
     * @return text with {@code tempDownloadToken} redacted
     */
    @Override
    public String toString() {
        return "GitHubRunnerApplication[os=" + os + ", architecture=" + architecture + ", downloadUrl=" + downloadUrl
                + ", filename=" + filename + ", tempDownloadToken=<redacted>, sha256Checksum=" + sha256Checksum + "]";
    }
}
