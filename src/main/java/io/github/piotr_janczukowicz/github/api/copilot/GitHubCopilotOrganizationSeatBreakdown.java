package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The breakdown of Copilot Business seats for the organization.
 *
 * @param total The total number of seats being billed for the organization as of the current billing cycle.
 * @param addedThisCycle Seats added during the current billing cycle.
 * @param pendingCancellation The number of seats that are pending cancellation at the end of the current billing cycle.
 * @param pendingInvitation The number of users who have been invited to receive a Copilot seat through this
 *     organization.
 * @param activeThisCycle The number of seats that have used Copilot during the current billing cycle.
 * @param inactiveThisCycle The number of seats that have not used Copilot during the current billing cycle.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/copilot-organization-seat-breakdown")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotOrganizationSeatBreakdown(
        @Nullable Integer total,
        @Nullable Integer addedThisCycle,
        @Nullable Integer pendingCancellation,
        @Nullable Integer pendingInvitation,
        @Nullable Integer activeThisCycle,
        @Nullable Integer inactiveThisCycle) {}
