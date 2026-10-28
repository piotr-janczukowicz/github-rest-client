package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.StringUtils.isNotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteEvaluateStatus;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteListResult;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteTimePeriod;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for organization rule suite list endpoints, excluding pagination.
 *
 * @param ref Ref filter
 * @param repositoryName Repository name filter
 * @param timePeriod Time period filter
 * @param actorName Actor name filter
 * @param ruleSuiteResult Result filter ({@code pass}, {@code fail}, {@code bypass}, {@code all})
 * @param evaluateStatus Evaluate status filter
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/repos/get-org-rule-suites/parameters")
@RecordBuilder
public record GitHubListOrgRuleSuitesFilter(
        @Nullable String ref,
        @Nullable String repositoryName,
        @Nullable GitHubRuleSuiteTimePeriod timePeriod,
        @Nullable String actorName,
        @Nullable GitHubRuleSuiteListResult ruleSuiteResult,
        @Nullable GitHubRuleSuiteEvaluateStatus evaluateStatus) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListOrgRuleSuitesFilter empty() {
        return GitHubListOrgRuleSuitesFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (isNotBlank(ref)) {
            query.put("ref", ref);
        }
        if (isNotBlank(repositoryName)) {
            query.put("repository_name", repositoryName);
        }
        if (timePeriod != null) {
            query.put("time_period", timePeriod.wireValue());
        }
        if (isNotBlank(actorName)) {
            query.put("actor_name", actorName);
        }
        if (ruleSuiteResult != null) {
            query.put("rule_suite_result", ruleSuiteResult.wireValue());
        }
        if (evaluateStatus != null) {
            query.put("evaluate_status", evaluateStatus.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
