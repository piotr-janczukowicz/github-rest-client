package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeast;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A public monitoring alert of an enterprise.
 *
 * @param number The enterprise-scoped public monitoring alert number.
 * @param url The REST API URL of the alert resource.
 * @param secretType The type of secret that secret scanning detected.
 * @param secretTypeDisplayName User-friendly name for the detected secret, matching the {@code secret_type}.
 * @param validity The validity status of the secret as of the latest validity check.
 * @param createdAt The time that the secret was detected in ISO 8601 format.
 * @param state The state of the alert.
 * @param resolution The reason for resolving the alert.
 * @param resolvedBy The user that resolved the alert.
 * @param resolvedAt The time that the alert was resolved in ISO 8601 format.
 * @param resolutionComment An optional comment when resolving or reopening an alert.
 * @param secret The secret that was detected.
 * @param repository The repository the secret was found in.
 * @param gist The gist the secret was found in.
 * @param attribution Attribution information for the alert.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/public-monitoring-alert")
@Flavors(GitHubFlavor.GHEC)
public record GitHubPublicMonitoringAlert(
        int number,
        String url,
        @Nullable String secretType,
        @Nullable String secretTypeDisplayName,
        @Nullable GitHubPublicMonitoringAlertValidity validity,
        @Nullable Instant createdAt,
        @Nullable GitHubPublicMonitoringAlertState state,
        @Nullable GitHubPublicMonitoringAlertResolution resolution,
        @Nullable GitHubSimpleUser resolvedBy,
        @Nullable Instant resolvedAt,
        @Nullable String resolutionComment,
        @Nullable String secret,
        @Nullable GitHubPublicMonitoringAlertRepository repository,
        @Nullable GitHubPublicMonitoringAlertGist gist,
        @Nullable GitHubPublicMonitoringAlertAttribution attribution) {
    public GitHubPublicMonitoringAlert {
        requireAtLeast(number, 1, "number");
        requireNonNull(url, "url");
    }

    /**
     * Describes this alert without the detected secret.
     *
     * @return text with {@code secret} redacted
     */
    @Override
    public String toString() {
        return "GitHubPublicMonitoringAlert[number=" + number + ", url=" + url + ", secretType=" + secretType
                + ", validity=" + validity + ", state=" + state + ", resolution=" + resolution + ", secret=<redacted>]";
    }
}
