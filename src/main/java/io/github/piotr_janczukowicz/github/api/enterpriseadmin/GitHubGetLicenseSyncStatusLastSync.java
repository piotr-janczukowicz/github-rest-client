package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Result of the last license sync of a server instance.
 *
 * @param date Date of the last sync
 * @param status Status of the last sync
 * @param error Error of the last sync
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get-license-sync-status/properties/server_instances/items/properties/last_sync")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetLicenseSyncStatusLastSync(
        @Nullable String date,
        @Nullable String status,
        @Nullable String error) {}
