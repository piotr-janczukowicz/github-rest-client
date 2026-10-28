package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Set Maintenance mode
 *
 * @param enabled Whether to enable maintenance mode.
 * @param uuid The UUID of the node to target. This parameter is incompatible with maintenance mode scheduling. Only use
 *     `uuid` if the value of `when` is empty or `now`.
 * @param when The time to enable maintenance mode. If this parameter is empty or set to `now`, maintenance mode is
 *     enabled immediately. Otherwise, maintenance mode is enabled at the specified time. The format is ISO 8601.
 * @param ipExceptionList The list of IP addresses to exclude from maintenance mode. IPv4, IPv6, and CIDR addresses are
 *     supported.
 * @param maintenanceModeMessage The message to display to users when maintenance mode is enabled.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-set-maintenance-request")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSetMaintenancePayload(
        boolean enabled,
        @Nullable String uuid,
        @Nullable Instant when,
        @Nullable List<String> ipExceptionList,
        @Nullable String maintenanceModeMessage) {
    public GitHubManageSetMaintenancePayload {
        if (ipExceptionList != null) {
            ipExceptionList = List.copyOf(ipExceptionList);
        }
    }
}
