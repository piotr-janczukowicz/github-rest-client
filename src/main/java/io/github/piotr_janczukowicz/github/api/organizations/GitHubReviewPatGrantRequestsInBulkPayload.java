package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Review organization programmatic access grant requests in bulk.
 *
 * @param patRequestIds Request identifiers to review
 * @param action Action to apply to the requests
 * @param reason Reason for approving or denying the requests; omit the wrapper to skip the field, or
 *     {@link OptionalNull#ofNull()} for JSON {@code null}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/review-pat-grant-requests-in-bulk/requestBody")
public record GitHubReviewPatGrantRequestsInBulkPayload(
        @Nullable List<Long> patRequestIds,
        GitHubOrganizationPatGrantReviewAction action,
        @Nullable OptionalNull<String> reason) {

    private static final String PARAM_PAT_REQUEST_IDS = "patRequestIds";
    private static final String PARAM_REASON = "reason";
    private static final int MIN_PAT_REQUEST_IDS = 1;
    private static final int MAX_PAT_REQUEST_IDS = 100;
    private static final int MAX_REASON_LENGTH = 1024;

    public GitHubReviewPatGrantRequestsInBulkPayload {
        requireNonNull(action, "action");
        if (patRequestIds != null) {
            patRequestIds = List.copyOf(
                    requireSizeBetween(patRequestIds, MIN_PAT_REQUEST_IDS, MAX_PAT_REQUEST_IDS, PARAM_PAT_REQUEST_IDS));
        }
        if (reason != null) {
            requireMaxLengthIfPresent(reason.value(), MAX_REASON_LENGTH, PARAM_REASON);
        }
    }
}
