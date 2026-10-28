package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GhesVersions;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageUpgradeStatus
 *
 * @param hostname The hostname of the node.
 * @param uuid The UUID of the node.
 * @param releaseVersion The current release version on the node.
 * @param releaseSha The current release SHA on the node.
 * @param status Overall upgrade status. One of: `not started`, `in progress`, `completed`, `failed`.
 * @param preUpgradeStatus Pre-upgrade phase status. One of: `not started`, `in progress`, `completed`, `failed`.
 * @param upgradeLastCompletedStep The name of the last completed upgrade step.
 * @param isRunning Whether the upgrade process is currently running.
 * @param failed Whether the upgrade has failed.
 * @param steps Individual step details.
 * @param messages Service failure messages. Only included when the upgrade has failed or in verbose mode.
 * @param error An error message if the request failed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-manage-upgrade-status-response/items")
@Flavors(GitHubFlavor.GHES)
@GhesVersions(since = "3.22")
public record GitHubManageUpgradeStatus(
        @Nullable String hostname,
        @Nullable String uuid,
        @Nullable String releaseVersion,
        @Nullable String releaseSha,
        @Nullable GitHubManageUpgradeStatusStatus status,
        @Nullable GitHubManageUpgradeStatusPreUpgradeStatus preUpgradeStatus,
        @Nullable String upgradeLastCompletedStep,
        @Nullable Boolean isRunning,
        @Nullable Boolean failed,
        @Nullable List<GitHubManageUpgradeStatusStep> steps,
        @Nullable List<String> messages,
        @Nullable String error) {}
