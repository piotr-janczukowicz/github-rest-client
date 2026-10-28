package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Rename branch request body.
 *
 * @param newName The new name of the branch
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/rename-branch/requestBody")
public record GitHubRenameBranchPayload(String newName) {

    private static final String PARAM_NEW_NAME = "newName";

    public GitHubRenameBranchPayload {
        requireNonNull(newName, PARAM_NEW_NAME);
    }
}
