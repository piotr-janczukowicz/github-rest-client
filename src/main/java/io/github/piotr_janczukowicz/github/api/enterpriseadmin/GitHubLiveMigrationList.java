package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubLiveMigrationList
 *
 * @param migrations migrations
 * @param totalCount total count
 * @param nextCursor next cursor
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/live-migration-list/200")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationList(
        List<GitHubLiveMigrationListMigration> migrations,
        int totalCount,
        @Nullable String nextCursor) {
    public GitHubLiveMigrationList {
        requireNonNull(migrations, "migrations");
    }
}
