package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * GitHubLiveMigrationDetailsTargetStateRepositoryProgress
 *
 * @param repositoryNwo repository nwo
 * @param backfillResourcesAdded backfill resources added
 * @param backfillResourcesProcessed backfill resources processed
 * @param backfillResourcesFailed backfill resources failed
 * @param backfillResourcesEligible backfill resources eligible
 * @param backfillResourcesAcknowledged backfill resources acknowledged
 * @param liveUpdateResourcesAdded live update resources added
 * @param liveUpdateResourcesProcessed live update resources processed
 * @param liveUpdateResourcesEligible live update resources eligible
 * @param liveUpdateResourcesAcknowledged live update resources acknowledged
 * @param liveUpdateResourcesFailed live update resources failed
 * @param allResourcesSent all resources sent
 * @param initialGitPushComplete initial git push complete
 * @param repositoryLocked repository locked
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/enterprise-admin/live-migration-get/200/properties/target_state/properties/repository_progress/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationDetailsTargetStateRepositoryProgress(
        String repositoryNwo,
        long backfillResourcesAdded,
        long backfillResourcesProcessed,
        long backfillResourcesFailed,
        long backfillResourcesEligible,
        long backfillResourcesAcknowledged,
        long liveUpdateResourcesAdded,
        long liveUpdateResourcesProcessed,
        long liveUpdateResourcesEligible,
        long liveUpdateResourcesAcknowledged,
        long liveUpdateResourcesFailed,
        boolean allResourcesSent,
        boolean initialGitPushComplete,
        boolean repositoryLocked) {
    public GitHubLiveMigrationDetailsTargetStateRepositoryProgress {
        requireNonNull(repositoryNwo, "repositoryNwo");
    }
}
