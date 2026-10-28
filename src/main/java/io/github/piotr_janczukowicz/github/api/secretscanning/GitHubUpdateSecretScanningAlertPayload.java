package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeastOne;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update secret scanning alert.
 *
 * @param state Sets the state of the secret scanning alert. You must provide `resolution` when you set the state to
 *     `resolved`.
 * @param resolution **Required when the `state` is `resolved`.** The reason for resolving the alert.
 * @param resolutionComment An optional comment when closing or reopening an alert. Cannot be updated or deleted.
 * @param assignee The username of the user to assign to the alert. Set to `null` to unassign the alert.
 * @param validity Sets the validity of the secret scanning alert. Can be `active`, `inactive`, or `null` to clear the
 *     override.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/secret-scanning/update-alert/requestBody")
public record GitHubUpdateSecretScanningAlertPayload(
        @Nullable GitHubSecretScanningAlertState state,
        @Nullable GitHubSecretScanningAlertResolution resolution,
        @Nullable OptionalNull<String> resolutionComment,
        @Nullable OptionalNull<String> assignee,
        @Nullable OptionalNull<GitHubSecretScanningAlertUpdateValidity> validity) {

    public GitHubUpdateSecretScanningAlertPayload {
        requireAtLeastOne(state, assignee, validity, "state, assignee or validity");
        if (state == GitHubSecretScanningAlertState.RESOLVED && resolution == null) {
            throw new IllegalArgumentException("resolution is required when state is resolved");
        }
    }
}
