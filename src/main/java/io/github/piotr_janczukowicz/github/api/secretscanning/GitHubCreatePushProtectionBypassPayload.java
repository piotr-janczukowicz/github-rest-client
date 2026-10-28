package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Create push protection bypass.
 *
 * @param reason The reason for bypassing push protection.
 * @param placeholderId The ID of the push protection bypass placeholder. This value is returned on any push protected
 *     routes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/secret-scanning/create-push-protection-bypass/requestBody")
public record GitHubCreatePushProtectionBypassPayload(
        GitHubSecretScanningPushProtectionBypassReason reason, String placeholderId) {

    public GitHubCreatePushProtectionBypassPayload {
        requireNonNull(reason, "reason");
        requireNonNull(placeholderId, "placeholderId");
    }
}
