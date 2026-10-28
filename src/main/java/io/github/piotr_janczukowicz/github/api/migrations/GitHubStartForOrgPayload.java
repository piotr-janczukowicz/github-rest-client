package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Start organization migration request body.
 *
 * @param repositories A list of arrays indicating which repositories should be migrated.
 * @param lockRepositories Indicates whether repositories should be locked (to prevent manipulation) while migrating
 *     data.
 * @param excludeMetadata Indicates whether metadata should be excluded and only git source should be included for the
 *     migration.
 * @param excludeGitData Indicates whether the repository git data should be excluded from the migration.
 * @param excludeAttachments Indicates whether attachments should be excluded from the migration (to reduce migration
 *     archive file size).
 * @param excludeReleases Indicates whether releases should be excluded from the migration (to reduce migration archive
 *     file size).
 * @param excludeOwnerProjects Indicates whether projects owned by the organization or users should be excluded. from
 *     the migration.
 * @param orgMetadataOnly Indicates whether this should only include organization metadata (repositories array should be
 *     empty and will ignore other flags).
 * @param exclude Exclude related items from being returned in the response in order to improve performance of the
 *     request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/migrations/start-for-org/requestBody")
public record GitHubStartForOrgPayload(
        List<String> repositories,
        @Nullable Boolean lockRepositories,
        @Nullable Boolean excludeMetadata,
        @Nullable Boolean excludeGitData,
        @Nullable Boolean excludeAttachments,
        @Nullable Boolean excludeReleases,
        @Nullable Boolean excludeOwnerProjects,
        @Nullable Boolean orgMetadataOnly,
        @Nullable List<GitHubMigrationExclude> exclude) {

    public GitHubStartForOrgPayload {
        requireNonNull(repositories, "repositories");
        repositories = List.copyOf(repositories);
        if (exclude != null) {
            exclude = List.copyOf(exclude);
        }
    }
}
