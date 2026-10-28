package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/** Query filters for {@code GET /repos/{owner}/{repo}/secret-scanning/alerts/{alert_number}}, excluding pagination. */
@CatalogSchema("operations/secret-scanning/get-alert/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubGetSecretScanningAlertFilter(@Nullable Boolean hideSecret) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetSecretScanningAlertFilter empty() {
        return GitHubGetSecretScanningAlertFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (hideSecret == null) {
            return Map.of();
        }
        return Map.of("hide_secret", hideSecret.toString());
    }
}
