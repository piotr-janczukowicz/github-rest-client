package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubLiveMigrationDetailsMigration
 *
 * @param migrationId migration id
 * @param status status
 * @param sourceOrganizationLogin source organization login
 * @param targetOrganizationLogin target organization login
 * @param sourceRepositoryName source repository name
 * @param targetRepositoryName target repository name
 * @param targetVisibility target visibility
 * @param targetApiEndpoint target api endpoint
 * @param targetMigrationId target migration id
 * @param createdAt created at
 * @param startedAt started at
 * @param completedAt completed at
 * @param expiresAt expires at
 * @param repositories repositories
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/live-migration-get/200/properties/migration")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationDetailsMigration(
        String migrationId,
        @Nullable String status,
        String sourceOrganizationLogin,
        String targetOrganizationLogin,
        String sourceRepositoryName,
        String targetRepositoryName,
        @Nullable String targetVisibility,
        String targetApiEndpoint,
        long targetMigrationId,
        @Nullable Instant createdAt,
        @Nullable Instant startedAt,
        @Nullable Instant completedAt,
        @Nullable Instant expiresAt,
        List<GitHubLiveMigrationDetailsMigrationRepository> repositories) {
    public GitHubLiveMigrationDetailsMigration {
        requireNonNull(migrationId, "migrationId");
        requireNonNull(sourceOrganizationLogin, "sourceOrganizationLogin");
        requireNonNull(targetOrganizationLogin, "targetOrganizationLogin");
        requireNonNull(sourceRepositoryName, "sourceRepositoryName");
        requireNonNull(targetRepositoryName, "targetRepositoryName");
        requireNonNull(targetApiEndpoint, "targetApiEndpoint");
        requireNonNull(repositories, "repositories");
    }
}
