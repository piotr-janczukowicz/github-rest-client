package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GhesVersions;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageApplyUpgradePayload
 *
 * @param upgradePackageFile Absolute path to the upgrade package file. Must be under `/var/lib/ghe-updates/` and the
 *     path must be canonical (no `..` or duplicate slashes).
 * @param version Target upgrade version in semantic version format (e.g. `3.19.3`). The version is normalized to
 *     `major.minor.patch` format.
 * @param targetPartition Target block device partition for the upgrade.
 * @param phase Upgrade phase to run. Valid values are `pre-upgrade` or `upgrade`. Omit for a full upgrade.
 * @param skipReboot Whether to skip the reboot after the upgrade completes. Default is `false`.
 * @param uuid The UUID of a specific node to upgrade. When omitted, the upgrade is applied to all nodes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-apply-manage-upgrade-request")
@Flavors(GitHubFlavor.GHES)
@GhesVersions(since = "3.22")
public record GitHubManageApplyUpgradePayload(
        @Nullable String upgradePackageFile,
        @Nullable String version,
        @Nullable String targetPartition,
        @Nullable GitHubManageApplyUpgradePayloadPhase phase,
        @Nullable Boolean skipReboot,
        @Nullable String uuid) {}
