package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A migration.
 *
 * @param id Id
 * @param nodeId Node id
 * @param owner Owner
 * @param guid Guid
 * @param state State
 * @param lockRepositories Lock repositories
 * @param excludeMetadata Exclude metadata
 * @param excludeGitData Exclude git data
 * @param excludeAttachments Exclude attachments
 * @param excludeReleases Exclude releases
 * @param excludeOwnerProjects Exclude owner projects
 * @param orgMetadataOnly Org metadata only
 * @param repositories The repositories included in the migration. Only returned for export migrations.
 * @param url Url
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param archiveUrl Archive url
 * @param exclude Exclude related items from being returned in the response in order to improve performance of the
 *     request. The array can include any of: {@code "repositories"}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/migration")
public record GitHubMigration(
        long id,
        String nodeId,
        @Nullable GitHubSimpleUser owner,
        String guid,
        String state,
        boolean lockRepositories,
        boolean excludeMetadata,
        boolean excludeGitData,
        boolean excludeAttachments,
        boolean excludeReleases,
        boolean excludeOwnerProjects,
        boolean orgMetadataOnly,
        List<GitHubSimpleRepository> repositories,
        String url,
        Instant createdAt,
        Instant updatedAt,
        @Nullable String archiveUrl,
        @Nullable List<String> exclude) {

    public GitHubMigration {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(guid, "guid");
        requireNonNull(state, "state");
        requireNonNull(repositories, "repositories");
        requireNonNull(url, "url");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
