package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * GitHubLiveMigrationCreateResponse
 *
 * @param migrationId Unique identifier for the new migration (UUID).
 * @param expiresAt Timestamp when the migration expires, or null if not set.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/live-migration-create/201")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationCreateResponse(
        String migrationId, @Nullable Instant expiresAt) {
    public GitHubLiveMigrationCreateResponse {
        requireNonNull(migrationId, "migrationId");
    }
}
