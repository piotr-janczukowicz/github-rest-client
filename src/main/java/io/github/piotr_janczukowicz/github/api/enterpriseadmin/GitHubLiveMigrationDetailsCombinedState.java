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
 * Derived, user-facing status reconciled from the exporter and destination states, including cutover readiness.
 *
 * @param status status
 * @param displayMessage display message
 * @param readyForCutover ready for cutover
 * @param cutoverBlockers cutover blockers
 * @param repositories repositories
 * @param terminalFailure Why the migration failed, for display to operators. Populated only when `status` is `failed`;
 *     `null` otherwise, including when the migration was terminated by a user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/live-migration-get/200/properties/combined_state")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationDetailsCombinedState(
        @Nullable String status,
        String displayMessage,
        boolean readyForCutover,
        List<String> cutoverBlockers,
        List<GitHubLiveMigrationDetailsCombinedStateRepository> repositories,
        @Nullable GitHubLiveMigrationDetailsCombinedStateTerminalFailure terminalFailure) {
    public GitHubLiveMigrationDetailsCombinedState {
        requireNonNull(displayMessage, "displayMessage");
        requireNonNull(cutoverBlockers, "cutoverBlockers");
        requireNonNull(repositories, "repositories");
    }
}
