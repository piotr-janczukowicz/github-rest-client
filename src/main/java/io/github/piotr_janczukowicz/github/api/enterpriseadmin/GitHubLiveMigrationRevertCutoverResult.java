package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * GitHubLiveMigrationRevertCutoverResult
 *
 * @param success Whether the cutover was successfully reverted.
 * @param unarchivedSourceRepository Whether the source repository was unarchived.
 * @param inProgressCutoverTerminated Whether an in-progress cutover was terminated.
 * @param inProgressMigrationTerminated Whether an in-progress migration was terminated.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/live-migration-revert-cutover/200")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationRevertCutoverResult(
        boolean success,
        boolean unarchivedSourceRepository,
        boolean inProgressCutoverTerminated,
        boolean inProgressMigrationTerminated) {}
