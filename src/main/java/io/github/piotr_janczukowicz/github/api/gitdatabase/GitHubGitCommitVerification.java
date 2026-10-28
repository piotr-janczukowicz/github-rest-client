package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Verification of a Git commit signature.
 *
 * @param verified Verified
 * @param reason Reason
 * @param signature Signature
 * @param payload Payload
 * @param verifiedAt Verified at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/git-commit/properties/verification")
public record GitHubGitCommitVerification(
        boolean verified,
        String reason,
        @Nullable String signature,
        @Nullable String payload,
        @Nullable String verifiedAt) {

    public GitHubGitCommitVerification {
        requireNonNull(reason, "reason");
    }
}
