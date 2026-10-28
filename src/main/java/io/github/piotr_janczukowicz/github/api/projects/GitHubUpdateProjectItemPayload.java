package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Update project item request body.
 *
 * @param fields Field updates
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/projects/update-item-for-org/requestBody",
    "operations/projects/update-item-for-user/requestBody"
})
public record GitHubUpdateProjectItemPayload(List<GitHubUpdateProjectItemFieldPayload> fields) {

    private static final String PARAM_FIELDS = "fields";

    public GitHubUpdateProjectItemPayload {
        fields = List.copyOf(requireNonNull(fields, PARAM_FIELDS));
    }
}
