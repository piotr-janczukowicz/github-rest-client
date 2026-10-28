package io.github.piotr_janczukowicz.github.api.deployments;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/deployments}, excluding pagination.
 *
 * @param sha The SHA recorded at creation time.
 * @param ref The name of the ref. This can be a branch, tag, or SHA.
 * @param task The name of the task for the deployment (e.g., {@code deploy} or {@code deploy:migrations}).
 * @param environment The name of the environment that was deployed to (e.g., {@code staging} or {@code production}).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/repos/list-deployments/parameters")
@RecordBuilder
public record GitHubListDeploymentsFilter(
        @Nullable String sha,
        @Nullable String ref,
        @Nullable String task,
        @Nullable String environment) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListDeploymentsFilter empty() {
        return GitHubListDeploymentsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (sha != null) {
            query.put("sha", sha);
        }
        if (ref != null) {
            query.put("ref", ref);
        }
        if (task != null) {
            query.put("task", task);
        }
        if (environment != null) {
            query.put("environment", environment);
        }

        return Collections.unmodifiableMap(query);
    }
}
