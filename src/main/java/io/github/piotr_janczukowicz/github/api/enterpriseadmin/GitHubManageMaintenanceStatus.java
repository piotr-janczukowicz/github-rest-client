package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageMaintenanceStatus
 *
 * @param hostname hostname
 * @param uuid uuid
 * @param status status
 * @param scheduledTime scheduled time
 * @param connectionServices connection services
 * @param canUnsetMaintenance can unset maintenance
 * @param ipExceptionList ip exception list
 * @param maintenanceModeMessage maintenance mode message
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-maintenance/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageMaintenanceStatus(
        @Nullable String hostname,
        @Nullable String uuid,
        @Nullable GitHubManageMaintenanceStatusStatus status,
        @Nullable String scheduledTime,
        @Nullable List<GitHubManageMaintenanceStatusConnectionService> connectionServices,
        @Nullable Boolean canUnsetMaintenance,
        @Nullable List<String> ipExceptionList,
        @Nullable String maintenanceModeMessage) {}
