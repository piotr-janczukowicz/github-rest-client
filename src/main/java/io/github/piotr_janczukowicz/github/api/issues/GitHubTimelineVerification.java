package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Verification of a timeline committed event.
 *
 * @param verified Verified
 * @param reason Reason
 * @param signature Signature
 * @param payload Payload
 * @param verifiedAt Verified at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/timeline-committed-event/properties/verification")
public record GitHubTimelineVerification(
        boolean verified,
        String reason,
        @Nullable String signature,
        @Nullable String payload,
        @Nullable String verifiedAt) {

    public GitHubTimelineVerification {
        requireNonNull(reason, "reason");
    }
}
