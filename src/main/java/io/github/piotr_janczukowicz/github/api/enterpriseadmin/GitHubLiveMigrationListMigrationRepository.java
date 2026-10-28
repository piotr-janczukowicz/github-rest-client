package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubLiveMigrationListMigrationRepository
 *
 * @param sourceRepositoryUrl source repository url
 * @param targetRepositoryUrl target repository url
 * @param status status
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/enterprise-admin/live-migration-list/200/properties/migrations/items/properties/repositories/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationListMigrationRepository(
        String sourceRepositoryUrl,
        String targetRepositoryUrl,
        @Nullable String status) {
    public GitHubLiveMigrationListMigrationRepository {
        requireNonNull(sourceRepositoryUrl, "sourceRepositoryUrl");
        requireNonNull(targetRepositoryUrl, "targetRepositoryUrl");
    }
}
