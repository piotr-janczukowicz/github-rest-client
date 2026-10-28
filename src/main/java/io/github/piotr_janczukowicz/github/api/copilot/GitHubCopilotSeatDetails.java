package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
import io.github.piotr_janczukowicz.github.api.teams.GitHubCopilotAssigningTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.time.LocalDate;
import org.jspecify.annotations.Nullable;

/**
 * Information about a Copilot Business seat assignment for a user, team, or organization.
 *
 * @param assignee Assignee
 * @param organization Organization
 * @param assigningTeam The team through which the assignee is granted access to GitHub Copilot, if applicable.
 * @param pendingCancellationDate The pending cancellation date for the seat, in {@code YYYY-MM-DD} format. This will be
 *     null unless the assignee's Copilot access has been canceled during the current billing cycle.
 * @param lastActivityAt Timestamp of user's last GitHub Copilot activity, in ISO 8601 format.
 * @param lastActivityEditor Last editor that was used by the user for a GitHub Copilot completion.
 * @param lastAuthenticatedAt Timestamp of the last time the user authenticated with GitHub Copilot, in ISO 8601 format.
 * @param createdAt Timestamp of when the assignee was last granted access to GitHub Copilot, in ISO 8601 format.
 * @param updatedAt <b>Closing down notice:</b> This field is no longer relevant and is closing down. Use the
 *     {@code created_at} field to determine when the assignee was last granted access to GitHub Copilot. Timestamp of
 *     when the assignee's GitHub Copilot access was last updated, in ISO 8601 format.
 * @param planType The Copilot plan of the organization, or the parent enterprise, when applicable.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/copilot-seat-details")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotSeatDetails(
        @Nullable GitHubSimpleUser assignee,
        @Nullable GitHubOrganizationSimple organization,
        @Nullable GitHubCopilotAssigningTeam assigningTeam,
        @Nullable LocalDate pendingCancellationDate,
        @Nullable Instant lastActivityAt,
        @Nullable String lastActivityEditor,
        @Nullable Instant lastAuthenticatedAt,
        Instant createdAt,
        @Deprecated @Nullable Instant updatedAt,
        @Nullable GitHubCopilotSeatPlanType planType) {

    public GitHubCopilotSeatDetails {
        requireNonNull(createdAt, "createdAt");
    }
}
