package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A server instance of a license sync job.
 *
 * @param serverId Identifier of the server instance
 * @param hostname Hostname of the server instance
 * @param lastSync Result of the last sync
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get-license-sync-status/properties/server_instances/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetLicenseSyncStatusServerInstance(
        @Nullable String serverId,
        @Nullable String hostname,
        @Nullable GitHubGetLicenseSyncStatusLastSync lastSync) {}
