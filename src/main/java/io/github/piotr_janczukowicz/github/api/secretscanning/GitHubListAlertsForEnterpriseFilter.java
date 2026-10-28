package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /enterprises/{enterprise}/secret-scanning/alerts}, excluding pagination.
 *
 * @param state alert state filter
 * @param secretType secret type filter
 * @param excludeSecretTypes excluded secret types filter
 * @param excludeProviders excluded providers filter
 * @param providers providers filter
 * @param resolution resolution filter
 * @param assignee assignee filter
 * @param sort sort field
 * @param direction sort direction
 * @param validity validity filter
 * @param isPubliclyLeaked publicly leaked filter
 * @param isMultiRepo multi-repo filter
 * @param hideSecret hide secret filter
 * @param isBypassed bypassed filter
 * @param includedMetadata included metadata filter
 * @param ownerEmailHash owner email hash filter
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/secret-scanning/list-alerts-for-enterprise/parameters")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubListAlertsForEnterpriseFilter(
        @Nullable GitHubSecretScanningAlertState state,
        @Nullable String secretType,
        @Nullable String excludeSecretTypes,
        @Nullable String excludeProviders,
        @Nullable String providers,
        @Nullable String resolution,
        @Nullable String assignee,
        @Nullable GitHubSecretScanningAlertSort sort,
        @Nullable GitHubDirection direction,
        @Nullable String validity,
        @Nullable Boolean isPubliclyLeaked,
        @Nullable Boolean isMultiRepo,
        @Nullable Boolean hideSecret,
        @Nullable Boolean isBypassed,
        @Nullable String includedMetadata,
        @Nullable String ownerEmailHash) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListAlertsForEnterpriseFilter empty() {
        return GitHubListAlertsForEnterpriseFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    @SuppressWarnings({"java:S3776", "PMD.CognitiveComplexity"})
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (state != null) {
            query.put("state", state.wireValue());
        }
        if (secretType != null) {
            query.put("secret_type", secretType);
        }
        if (excludeSecretTypes != null) {
            query.put("exclude_secret_types", excludeSecretTypes);
        }
        if (excludeProviders != null) {
            query.put("exclude_providers", excludeProviders);
        }
        if (providers != null) {
            query.put("providers", providers);
        }
        if (resolution != null) {
            query.put("resolution", resolution);
        }
        if (assignee != null) {
            query.put("assignee", assignee);
        }
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }
        if (direction != null) {
            query.put("direction", direction.wireValue());
        }
        if (validity != null) {
            query.put("validity", validity);
        }
        if (isPubliclyLeaked != null) {
            query.put("is_publicly_leaked", isPubliclyLeaked.toString());
        }
        if (isMultiRepo != null) {
            query.put("is_multi_repo", isMultiRepo.toString());
        }
        if (hideSecret != null) {
            query.put("hide_secret", hideSecret.toString());
        }
        if (isBypassed != null) {
            query.put("is_bypassed", isBypassed.toString());
        }
        if (includedMetadata != null) {
            query.put("included_metadata", includedMetadata);
        }
        if (ownerEmailHash != null) {
            query.put("owner_email_hash", ownerEmailHash);
        }

        return Collections.unmodifiableMap(query);
    }
}
