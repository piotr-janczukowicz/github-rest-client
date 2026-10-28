package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create project view request body.
 *
 * @param name View name
 * @param layout View layout
 * @param filter Filter query
 * @param visibleFields Visible field ids
 * @param sortBy Sort configuration
 * @param groupBy Horizontal grouping field ids
 * @param verticalGroupBy Vertical grouping field ids
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/projects/create-view-for-user/requestBody",
    "operations/projects/create-view-for-org/requestBody"
})
public record GitHubCreateProjectViewPayload(
        String name,
        GitHubProjectViewLayout layout,
        @Nullable String filter,
        @Nullable @JsonProperty("visible_fields") List<Long> visibleFields,
        @Nullable @JsonProperty("sort_by") List<GitHubProjectViewSort> sortBy,
        @Nullable @JsonProperty("group_by") List<Long> groupBy,
        @Nullable @JsonProperty("vertical_group_by") List<Long> verticalGroupBy) {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_LAYOUT = "layout";
    private static final String PARAM_VISIBLE_FIELDS = "visibleFields";
    private static final String PARAM_SORT_BY = "sortBy";
    private static final String PARAM_GROUP_BY = "groupBy";
    private static final String PARAM_VERTICAL_GROUP_BY = "verticalGroupBy";

    public GitHubCreateProjectViewPayload {
        requireNonNull(name, PARAM_NAME);
        requireNonNull(layout, PARAM_LAYOUT);

        if (layout == GitHubProjectViewLayout.ROADMAP && visibleFields != null) {
            throw new IllegalArgumentException(PARAM_VISIBLE_FIELDS + " is not applicable to roadmap layout");
        }

        if (visibleFields != null) {
            visibleFields = List.copyOf(visibleFields);
        }
        if (sortBy != null) {
            sortBy = List.copyOf(sortBy);
            if (sortBy.stream().anyMatch(sort -> sort.direction() == GitHubProjectViewSortDirection.UNKNOWN)) {
                throw new IllegalArgumentException(PARAM_SORT_BY + " must not contain an unknown direction");
            }
        }
        if (groupBy != null) {
            groupBy = List.copyOf(requireSizeAtMost(groupBy, 1, PARAM_GROUP_BY));
        }
        if (verticalGroupBy != null) {
            verticalGroupBy = List.copyOf(requireSizeAtMost(verticalGroupBy, 1, PARAM_VERTICAL_GROUP_BY));
        }
    }
}
