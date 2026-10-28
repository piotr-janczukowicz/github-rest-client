package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GhesVersions;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageApplyUpgradeResult
 *
 * @param hostname The hostname of the node.
 * @param uuid The UUID of the node.
 * @param message A message indicating whether the upgrade or pre-upgrade was started.
 * @param error An error message if the request failed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-apply-manage-upgrade-response/items")
@Flavors(GitHubFlavor.GHES)
@GhesVersions(since = "3.22")
public record GitHubManageApplyUpgradeResult(
        @Nullable String hostname,
        @Nullable String uuid,
        @Nullable String message,
        @Nullable String error) {}
