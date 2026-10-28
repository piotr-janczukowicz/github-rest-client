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
 * State of the migration on the destination (ELM backend). Aggregate counts only; per-node detail is not included.
 *
 * @param status status
 * @param lastUpdated last updated
 * @param targetUnavailable True when the destination was unreachable during this query. The combined status is reported
 *     as `degraded` in that case.
 * @param repositorySummaries repository summaries
 * @param repositoryProgress repository progress
 * @param terminalFailure Why the destination reached a terminal failure, as reported by the destination. `null` when
 *     the destination is unreachable or has not failed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/live-migration-get/200/properties/target_state")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationDetailsTargetState(
        @Nullable String status,
        @Nullable Instant lastUpdated,
        boolean targetUnavailable,
        List<GitHubLiveMigrationDetailsTargetStateRepositorySummary> repositorySummaries,
        List<GitHubLiveMigrationDetailsTargetStateRepositoryProgress> repositoryProgress,
        @Nullable GitHubLiveMigrationDetailsTargetStateTerminalFailure terminalFailure) {
    public GitHubLiveMigrationDetailsTargetState {
        requireNonNull(repositorySummaries, "repositorySummaries");
        requireNonNull(repositoryProgress, "repositoryProgress");
    }
}
