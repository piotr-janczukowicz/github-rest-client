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
 * Why the migration failed, for display to operators. Populated only when `status` is `failed`; `null` otherwise,
 * including when the migration was terminated by a user.
 *
 * @param code Stable, machine-readable failure category, such as `repository policy` or `organization settings`. New
 *     values may be added; clients should treat unrecognized values as an unknown cause.
 * @param summary Human-readable explanation of the failure category.
 * @param occurredAt When the failure occurred. `null` when no time was recorded.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/enterprise-admin/live-migration-get/200/properties/combined_state/properties/terminal_failure")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationDetailsCombinedStateTerminalFailure(
        String code, String summary, @Nullable Instant occurredAt) {
    public GitHubLiveMigrationDetailsCombinedStateTerminalFailure {
        requireNonNull(code, "code");
        requireNonNull(summary, "summary");
    }
}
