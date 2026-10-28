package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A Dependabot alert.
 *
 * @param number The security alert number.
 * @param state The state of the Dependabot alert.
 * @param dependency Details for the vulnerable dependency.
 * @param securityAdvisory Details for the GitHub Security Advisory.
 * @param securityVulnerability Details pertaining to one vulnerable version range for the advisory.
 * @param url The REST API URL of the alert resource.
 * @param htmlUrl The GitHub URL of the alert resource.
 * @param createdAt The time that the alert was created in ISO 8601 format: {@code YYYY-MM-DDTHH:MM:SSZ}.
 * @param updatedAt The time that the alert was last updated in ISO 8601 format: {@code YYYY-MM-DDTHH:MM:SSZ}.
 * @param dismissedAt The time that the alert was dismissed in ISO 8601 format: {@code YYYY-MM-DDTHH:MM:SSZ}.
 * @param dismissedBy Dismissed by
 * @param dismissedReason The reason that the alert was dismissed.
 * @param dismissedComment An optional comment associated with the alert's dismissal.
 * @param fixedAt The time that the alert was no longer detected and was considered fixed in ISO 8601 format:
 *     {@code YYYY-MM-DDTHH:MM:SSZ}.
 * @param autoDismissedAt The time that the alert was auto-dismissed in ISO 8601 format: {@code YYYY-MM-DDTHH:MM:SSZ}.
 * @param dismissalRequest Information about an active dismissal request for this Dependabot alert.
 * @param assignees The users assigned to this alert.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-alert")
public record GitHubDependabotAlert(
        int number,
        GitHubDependabotAlertState state,
        GitHubDependabotAlertDependency dependency,
        GitHubDependabotAlertSecurityAdvisory securityAdvisory,
        GitHubDependabotAlertSecurityVulnerability securityVulnerability,
        String url,
        String htmlUrl,
        Instant createdAt,
        Instant updatedAt,
        @Nullable Instant dismissedAt,
        @Nullable GitHubSimpleUser dismissedBy,
        @Nullable GitHubDependabotAlertDismissedReason dismissedReason,
        @Nullable String dismissedComment,
        @Nullable Instant fixedAt,
        @Nullable Instant autoDismissedAt,
        @Nullable GitHubDependabotAlertDismissalRequest dismissalRequest,
        @Nullable List<GitHubSimpleUser> assignees) {

    private static final int MAX_DISMISSED_COMMENT_LENGTH = 280;

    public GitHubDependabotAlert {
        requireNonNull(state, "state");
        requireNonNull(dependency, "dependency");
        requireNonNull(securityAdvisory, "securityAdvisory");
        requireNonNull(securityVulnerability, "securityVulnerability");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        dismissedComment =
                requireMaxLengthIfPresent(dismissedComment, MAX_DISMISSED_COMMENT_LENGTH, "dismissedComment");
    }
}
