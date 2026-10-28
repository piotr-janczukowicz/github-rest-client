package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/projectsV2/{project_number}/views/{view_number}/items}, {@code GET
 * /users/{username}/projectsV2/{project_number}/views/{view_number}/items}, {@code GET
 * /orgs/{org}/projectsV2/{project_number}/items/{item_id}}, and {@code GET
 * /users/{username}/projectsV2/{project_number}/items/{item_id}}, excluding pagination.
 *
 * @param fields Field ids to include
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "operations/projects/list-view-items-for-org/parameters",
    "operations/projects/list-view-items-for-user/parameters",
    "operations/projects/get-org-item/parameters",
    "operations/projects/get-user-item/parameters"
})
@RecordBuilder
public record GitHubListProjectViewItemsFilter(
        @Nullable @CatalogSimpleXor("string|array") List<String> fields) {

    private static final String PARAM_FIELDS = "fields";
    private static final int MAX_FIELDS = 50;

    public GitHubListProjectViewItemsFilter {
        if (fields != null) {
            fields = List.copyOf(requireSizeAtMost(fields, MAX_FIELDS, PARAM_FIELDS));
        }
    }

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListProjectViewItemsFilter empty() {
        return GitHubListProjectViewItemsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (fields == null) {
            return Map.of();
        }

        return Map.of(PARAM_FIELDS, String.join(",", fields));
    }
}
