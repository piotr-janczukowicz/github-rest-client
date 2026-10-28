package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/projectsV2/{project_number}/items} and {@code GET
 * /users/{username}/projectsV2/{project_number}/items}, excluding pagination.
 *
 * @param q Search query
 * @param fields Field ids to include
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "operations/projects/list-items-for-org/parameters",
    "operations/projects/list-items-for-user/parameters"
})
@RecordBuilder
public record GitHubListProjectItemsFilter(
        @Nullable String q,
        @Nullable @CatalogSimpleXor("string|array") List<String> fields) {

    private static final String PARAM_FIELDS = "fields";
    private static final int MAX_FIELDS = 50;

    public GitHubListProjectItemsFilter {
        if (fields != null) {
            fields = List.copyOf(requireSizeAtMost(fields, MAX_FIELDS, PARAM_FIELDS));
        }
    }

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListProjectItemsFilter empty() {
        return GitHubListProjectItemsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (q == null && fields == null) {
            return Map.of();
        }

        final Map<String, String> parameters = new LinkedHashMap<>();
        if (q != null) {
            parameters.put("q", q);
        }
        if (fields != null) {
            parameters.put(PARAM_FIELDS, String.join(",", fields));
        }
        return Collections.unmodifiableMap(parameters);
    }
}
