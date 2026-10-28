package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Verification.
 *
 * @param verified Verified
 * @param reason Reason
 * @param payload Payload
 * @param signature Signature
 * @param verifiedAt Verified at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/verification")
public record GitHubVerification(
        boolean verified,
        String reason,
        @Nullable String payload,
        @Nullable String signature,

        @SuppressSchemaTypeMismatch("dump type is string or null and omits format date-time") @Nullable Instant verifiedAt) {

    public GitHubVerification {
        requireNonNull(reason, "reason");
    }
}
