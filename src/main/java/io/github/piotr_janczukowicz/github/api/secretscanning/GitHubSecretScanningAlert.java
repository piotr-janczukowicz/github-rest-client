package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Secret scanning alert.
 *
 * @param number The security alert number.
 * @param createdAt The time that the alert was created in ISO 8601 format: `YYYY-MM-DDTHH:MM:SSZ`.
 * @param updatedAt The time that the alert was last updated in ISO 8601 format: `YYYY-MM-DDTHH:MM:SSZ`.
 * @param url The REST API URL of the alert resource.
 * @param htmlUrl The GitHub URL of the alert resource.
 * @param locationsUrl The REST API URL of the code locations for this alert.
 * @param state Sets the state of the secret scanning alert. You must provide `resolution` when you set the state to
 *     `resolved`.
 * @param resolution **Required when the `state` is `resolved`.** The reason for resolving the alert.
 * @param resolvedAt The time that the alert was resolved in ISO 8601 format: `YYYY-MM-DDTHH:MM:SSZ`.
 * @param resolvedBy Resolved by
 * @param resolutionComment An optional comment to resolve an alert.
 * @param secretType The type of secret that secret scanning detected.
 * @param secretTypeDisplayName User-friendly name for the detected secret, matching the `secret_type`. For a list of
 *     built-in patterns, see "[Supported secret scanning
 *     patterns](https://docs.github.com/code-security/secret-scanning/introduction/supported-secret-scanning-patterns#supported-secrets)."
 * @param provider The provider of the secret that was detected.
 * @param providerSlug The slug identifier for the provider of the secret that was detected. Use this value for
 *     filtering by provider with the `providers` or `exclude_providers` parameters.
 * @param secret The secret that was detected.
 * @param pushProtectionBypassed Whether push protection was bypassed for the detected secret.
 * @param pushProtectionBypassedBy Push protection bypassed by
 * @param pushProtectionBypassedAt The time that push protection was bypassed in ISO 8601 format:
 *     `YYYY-MM-DDTHH:MM:SSZ`.
 * @param pushProtectionBypassRequestReviewer Push protection bypass request reviewer
 * @param pushProtectionBypassRequestReviewerComment An optional comment when reviewing a push protection bypass.
 * @param pushProtectionBypassRequestComment An optional comment when requesting a push protection bypass.
 * @param pushProtectionBypassRequestHtmlUrl The URL to a push protection bypass request.
 * @param validity The token status as of the latest validity check.
 * @param publiclyLeaked Whether the detected secret was publicly leaked.
 * @param multiRepo Whether the detected secret was found in multiple repositories under the same organization or
 *     enterprise.
 * @param isBase64Encoded A boolean value representing whether or not alert is base64 encoded
 * @param firstLocationDetected First location detected
 * @param hasMoreLocations A boolean value representing whether or not the token in the alert was detected in more than
 *     one location.
 * @param assignedTo Assigned to
 * @param closureRequestComment An optional comment from the closure request author.
 * @param closureRequestReviewerComment An optional comment from the closure request reviewer.
 * @param closureRequestReviewer Closure request reviewer
 */
@CatalogSchema("components/schemas/secret-scanning-alert")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningAlert(
        @Nullable Integer number,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt,
        @Nullable String url,
        @Nullable String htmlUrl,
        @Nullable String locationsUrl,
        @Nullable GitHubSecretScanningAlertState state,
        @Nullable GitHubSecretScanningAlertResolution resolution,
        @Nullable Instant resolvedAt,
        @Nullable GitHubSimpleUser resolvedBy,
        @Nullable String resolutionComment,
        @Nullable String secretType,
        @Nullable String secretTypeDisplayName,
        @Nullable String provider,
        @Nullable String providerSlug,
        @Nullable String secret,
        @Nullable Boolean pushProtectionBypassed,
        @Nullable GitHubSimpleUser pushProtectionBypassedBy,
        @Nullable Instant pushProtectionBypassedAt,
        @Nullable GitHubSimpleUser pushProtectionBypassRequestReviewer,
        @Nullable String pushProtectionBypassRequestReviewerComment,
        @Nullable String pushProtectionBypassRequestComment,
        @Nullable String pushProtectionBypassRequestHtmlUrl,
        @Nullable GitHubSecretScanningAlertValidity validity,
        @Nullable Boolean publiclyLeaked,
        @Nullable Boolean multiRepo,
        @Nullable Boolean isBase64Encoded,
        @Nullable GitHubSecretScanningFirstDetectedLocation firstLocationDetected,
        @Nullable Boolean hasMoreLocations,
        @Nullable GitHubSimpleUser assignedTo,
        @Nullable String closureRequestComment,
        @Nullable String closureRequestReviewerComment,
        @Nullable GitHubSimpleUser closureRequestReviewer) {

    /**
     * Describes this GitHubSecretScanningAlert without credential material.
     *
     * @return text with {@code secret} redacted
     */
    @Override
    public String toString() {
        return "GitHubSecretScanningAlert[number=" + number + ", state=" + state + ", secretType=" + secretType
                + ", url=" + url + ", secret=<redacted>]";
    }
}
