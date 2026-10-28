package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Review an organization programmatic access grant request.
 *
 * @param action Action to apply to the request
 * @param reason Reason for approving or denying the request; omit the wrapper to skip the field, or
 *     {@link OptionalNull#ofNull()} for JSON {@code null}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/review-pat-grant-request/requestBody")
public record GitHubReviewPatGrantRequestPayload(
        GitHubOrganizationPatGrantReviewAction action,
        @Nullable OptionalNull<String> reason) {

    private static final String PARAM_REASON = "reason";
    private static final int MAX_REASON_LENGTH = 1024;

    public GitHubReviewPatGrantRequestPayload {
        requireNonNull(action, "action");
        if (reason != null) {
            requireMaxLengthIfPresent(reason.value(), MAX_REASON_LENGTH, PARAM_REASON);
        }
    }
}
