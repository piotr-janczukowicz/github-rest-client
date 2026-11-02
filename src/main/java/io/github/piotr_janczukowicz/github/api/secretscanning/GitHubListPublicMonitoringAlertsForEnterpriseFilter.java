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
 * Query filters for {@code GET /enterprises/{enterprise}/secret-scanning/public-monitoring-alerts}, excluding
 * pagination.
 *
 * @param direction The direction to sort the results by.
 * @param repository A comma-separated list of repositories to filter by.
 * @param secretAuthor A comma-separated list of secret authors to filter by.
 * @param secretType A comma-separated list of secret types to filter by.
 * @param attribution The attribution method to filter by.
 * @param validity A comma-separated list of validity statuses to filter by.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/secret-scanning/list-public-monitoring-alerts-for-enterprise/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubListPublicMonitoringAlertsForEnterpriseFilter(
        @Nullable GitHubDirection direction,
        @Nullable String repository,
        @Nullable String secretAuthor,
        @Nullable String secretType,
        @Nullable GitHubListPublicMonitoringAlertsForEnterpriseAttribution attribution,
        @Nullable String validity) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListPublicMonitoringAlertsForEnterpriseFilter empty() {
        return GitHubListPublicMonitoringAlertsForEnterpriseFilterBuilder.builder()
                .build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (direction != null) {
            query.put("direction", direction.wireValue());
        }
        if (repository != null) {
            query.put("repository", repository);
        }
        if (secretAuthor != null) {
            query.put("secret_author", secretAuthor);
        }
        if (secretType != null) {
            query.put("secret_type", secretType);
        }
        if (attribution != null) {
            query.put("attribution", attribution.wireValue());
        }
        if (validity != null) {
            query.put("validity", validity);
        }

        return Collections.unmodifiableMap(query);
    }

    /**
     * Describes this filter. {@code secretAuthor} is a login, not a credential.
     *
     * @return text with every filter value
     */
    @Override
    public String toString() {
        return "GitHubListPublicMonitoringAlertsForEnterpriseFilter[direction=" + direction + ", repository="
                + repository + ", secretAuthor=" + secretAuthor + ", secretType=" + secretType + ", attribution="
                + attribution + ", validity=" + validity + "]";
    }
}
