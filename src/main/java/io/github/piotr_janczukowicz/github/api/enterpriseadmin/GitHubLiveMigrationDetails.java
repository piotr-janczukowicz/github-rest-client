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
 * GitHubLiveMigrationDetails
 *
 * @param migration migration
 * @param targetState State of the migration on the destination (ELM backend). Aggregate counts only; per-node detail is
 *     not included.
 * @param combinedState Derived, user-facing status reconciled from the exporter and destination states, including
 *     cutover readiness.
 * @param messages Informational and error messages associated with the migration (for example, preflight results).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/live-migration-get/200")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationDetails(
        @Nullable GitHubLiveMigrationDetailsMigration migration,
        @Nullable GitHubLiveMigrationDetailsTargetState targetState,
        @Nullable GitHubLiveMigrationDetailsCombinedState combinedState,
        List<GitHubLiveMigrationDetailsMessage> messages) {
    public GitHubLiveMigrationDetails {
        requireNonNull(messages, "messages");
    }
}
