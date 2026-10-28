package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A repository import from an external source.
 *
 * @param vcs Vcs
 * @param useLfs Use lfs
 * @param vcsUrl The URL of the originating repository.
 * @param svcRoot Svc root
 * @param tfvcProject Tfvc project
 * @param status Status
 * @param statusText Status text
 * @param failedStep Failed step
 * @param errorMessage Error message
 * @param importPercent Import percent
 * @param commitCount Commit count
 * @param pushPercent Push percent
 * @param hasLargeFiles Has large files
 * @param largeFilesSize Large files size
 * @param largeFilesCount Large files count
 * @param projectChoices Project choices
 * @param message Message
 * @param authorsCount Authors count
 * @param url Url
 * @param htmlUrl Html url
 * @param authorsUrl Authors url
 * @param repositoryUrl Repository url
 * @param svnRoot Svn root
 * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this endpoint
 *     is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
 *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
 */
@Deprecated(since = "2023-10-12")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/import")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubImport(
        @Nullable String vcs,

        @Nullable Boolean useLfs,

        String vcsUrl,
        @Nullable String svcRoot,
        @Nullable String tfvcProject,
        GitHubImportStatus status,
        @Nullable String statusText,
        @Nullable String failedStep,
        @Nullable String errorMessage,
        @Nullable Integer importPercent,
        @Nullable Integer commitCount,
        @Nullable Integer pushPercent,
        @Nullable Boolean hasLargeFiles,
        @Nullable Integer largeFilesSize,
        @Nullable Integer largeFilesCount,
        @Nullable List<ProjectChoice> projectChoices,
        @Nullable String message,
        @Nullable Integer authorsCount,
        String url,
        String htmlUrl,
        String authorsUrl,
        String repositoryUrl,
        @Nullable String svnRoot) {

    public GitHubImport {
        requireNonNull(vcsUrl, "vcsUrl");
        requireNonNull(status, "status");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(authorsUrl, "authorsUrl");
        requireNonNull(repositoryUrl, "repositoryUrl");
    }

    /** Project choice returned when multiple projects are detected at the import URL. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    @CatalogSchema("components/schemas/import/properties/project_choices/items")
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public record ProjectChoice(
            @Nullable String vcs,
            @Nullable String tfvcProject,
            @Nullable String humanName) {}
}
