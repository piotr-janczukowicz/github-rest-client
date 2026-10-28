package io.github.piotr_janczukowicz.github.api.dependabot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/dependabot/alerts}, excluding pagination.
 *
 * @param classification A comma-separated list of vulnerability classifications.
 * @param state A comma-separated list of states.
 * @param severity A comma-separated list of severities.
 * @param ecosystem A comma-separated list of ecosystems.
 * @param packages A comma-separated list of package names.
 * @param epssPercentage CVE Exploit Prediction Scoring System (EPSS) percentage, as an exact number, a comparator or a
 *     range.
 * @param artifactRegistryUrl A comma-separated list of artifact registry URLs.
 * @param artifactRegistry A comma-separated list of Artifact Registry name strings.
 * @param has Filters the list of alerts based on whether the alert has the given value.
 * @param assignee Filter alerts by assignees.
 * @param runtimeRisk A comma-separated list of runtime risk strings.
 * @param scope The scope of the vulnerable dependency.
 * @param relationship A comma-separated list of relationships of the vulnerable dependency to your project.
 * @param sort The property by which to sort the results.
 * @param direction The direction to sort the results by.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/dependabot/list-alerts-for-org/parameters")
public record GitHubListAlertsForOrgFilter(
        @Nullable String classification,
        @Nullable String state,
        @Nullable String severity,
        @Nullable String ecosystem,
        @JsonProperty("package") @Nullable String packages,
        @Nullable String epssPercentage,
        @Nullable String artifactRegistryUrl,
        @Nullable String artifactRegistry,
        @Nullable @CatalogSimpleXor("string|array") List<String> has,
        @Nullable String assignee,
        @Nullable String runtimeRisk,
        @Nullable GitHubDependabotAlertScope scope,
        @Nullable String relationship,
        @Nullable GitHubDependabotAlertSort sort,
        @Nullable GitHubDirection direction) {

    public GitHubListAlertsForOrgFilter {
        if (has != null) {
            has = List.copyOf(has);
        }
    }

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListAlertsForOrgFilter empty() {
        return GitHubListAlertsForOrgFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    @SuppressWarnings({"java:S3776", "PMD.CognitiveComplexity"})
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (classification != null) {
            query.put("classification", classification);
        }
        if (state != null) {
            query.put("state", state);
        }
        if (severity != null) {
            query.put("severity", severity);
        }
        if (ecosystem != null) {
            query.put("ecosystem", ecosystem);
        }
        if (packages != null) {
            query.put("package", packages);
        }
        if (epssPercentage != null) {
            query.put("epss_percentage", epssPercentage);
        }
        if (artifactRegistryUrl != null) {
            query.put("artifact_registry_url", artifactRegistryUrl);
        }
        if (artifactRegistry != null) {
            query.put("artifact_registry", artifactRegistry);
        }
        if (has != null) {
            query.put("has", String.join(",", has));
        }
        if (assignee != null) {
            query.put("assignee", assignee);
        }
        if (runtimeRisk != null) {
            query.put("runtime_risk", runtimeRisk);
        }
        if (scope != null) {
            query.put("scope", scope.wireValue());
        }
        if (relationship != null) {
            query.put("relationship", relationship);
        }
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }
        if (direction != null) {
            query.put("direction", direction.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
