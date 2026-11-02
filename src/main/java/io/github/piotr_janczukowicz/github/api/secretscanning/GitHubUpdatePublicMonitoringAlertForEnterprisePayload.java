package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request body for updating a public monitoring alert of an enterprise.
 *
 * @param state Sets the state of the secret scanning alert. You must provide {@code resolution} when you set the state
 *     to {@code resolved}.
 * @param resolution Required when the {@code state} is {@code resolved}. The reason for resolving the alert.
 * @param resolutionComment An optional comment when closing or reopening an alert. Cannot be updated or deleted.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/secret-scanning/update-public-monitoring-alert-for-enterprise/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdatePublicMonitoringAlertForEnterprisePayload(
        GitHubSecretScanningAlertState state,
        @Nullable GitHubUpdatePublicMonitoringAlertForEnterpriseResolution resolution,
        @Nullable String resolutionComment) {
    public GitHubUpdatePublicMonitoringAlertForEnterprisePayload {
        requireNonNull(state, "state");
        if (state == GitHubSecretScanningAlertState.RESOLVED && resolution == null) {
            throw new IllegalArgumentException("resolution is required when state is resolved");
        }
    }
}
