package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Sort criterion for a project view.
 *
 * @param fieldId Field id
 * @param direction Sort direction
 */
@RecordBuilder
@CatalogSchema({
    "components/schemas/projects-v2-view/properties/sort_by/items",
    "operations/projects/create-view-for-org/requestBody/properties/sort_by/items",
    "operations/projects/create-view-for-user/requestBody/properties/sort_by/items"
})
public record GitHubProjectViewSort(long fieldId, GitHubProjectViewSortDirection direction) {

    public GitHubProjectViewSort {
        requireNonNull(direction, "direction");
    }

    /**
     * Serializes as a two-element array {@code [field_id, direction]}.
     *
     * @return wire tuple
     */
    @JsonValue
    public List<Object> toJson() {
        return List.of(fieldId, direction.wireValue());
    }
}
