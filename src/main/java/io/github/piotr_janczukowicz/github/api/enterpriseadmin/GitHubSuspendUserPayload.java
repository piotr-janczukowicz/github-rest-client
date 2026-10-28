package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubSuspendUserPayload
 *
 * @param reason The reason the user is being suspended. This message will be logged in the [audit
 *     log](https://docs.github.com/enterprise-server@3.22/admin/monitoring-activity-in-your-enterprise/reviewing-audit-logs-for-your-enterprise/about-the-audit-log-for-your-enterprise).
 *     If you don't provide a `reason`, it will default to "Suspended via API by SITE\ ADMINISTRATOR ", where SITE\
 *     ADMINISTRATOR is the person who performed the action.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/suspend-user/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubSuspendUserPayload(@Nullable String reason) {}
