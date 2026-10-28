package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Secret scanning push protection bypass.
 *
 * @param reason The reason for bypassing push protection.
 * @param expireAt The time that the bypass will expire in ISO 8601 format: `YYYY-MM-DDTHH:MM:SSZ`.
 * @param tokenType The token type this bypass is for.
 */
@CatalogSchema("components/schemas/secret-scanning-push-protection-bypass")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningPushProtectionBypass(
        @Nullable GitHubSecretScanningPushProtectionBypassReason reason,
        @Nullable Instant expireAt,
        @Nullable String tokenType) {}
