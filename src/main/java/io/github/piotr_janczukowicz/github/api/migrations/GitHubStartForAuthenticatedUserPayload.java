package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Start user migration request body.
 *
 * @param repositories Repositories to migrate
 * @param lockRepositories Lock the repositories being migrated at the start of the migration
 * @param excludeMetadata Indicates whether metadata should be excluded and only git source should be included for the
 *     migration.
 * @param excludeGitData Indicates whether the repository git data should be excluded from the migration.
 * @param excludeAttachments Do not include attachments in the migration
 * @param excludeReleases Do not include releases in the migration
 * @param excludeOwnerProjects Indicates whether projects owned by the organization or users should be excluded.
 * @param orgMetadataOnly Indicates whether this should only include organization metadata (repositories array should be
 *     empty and will ignore other flags).
 * @param exclude Exclude attributes from the API response to improve performance
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/migrations/start-for-authenticated-user/requestBody")
public record GitHubStartForAuthenticatedUserPayload(
        List<String> repositories,
        @Nullable Boolean lockRepositories,
        @Nullable Boolean excludeMetadata,
        @Nullable Boolean excludeGitData,
        @Nullable Boolean excludeAttachments,
        @Nullable Boolean excludeReleases,
        @Nullable Boolean excludeOwnerProjects,
        @Nullable Boolean orgMetadataOnly,
        @Nullable List<GitHubMigrationExclude> exclude) {

    public GitHubStartForAuthenticatedUserPayload {
        requireNonNull(repositories, "repositories");
        repositories = List.copyOf(repositories);
        if (exclude != null) {
            exclude = List.copyOf(exclude);
        }
    }
}
