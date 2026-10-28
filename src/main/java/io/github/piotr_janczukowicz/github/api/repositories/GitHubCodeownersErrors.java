package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * CODEOWNERS errors.
 *
 * @param errors Syntax errors in the CODEOWNERS file.
 */
@CatalogSchema("components/schemas/codeowners-errors")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubCodeownersErrors(List<GitHubCodeownersError> errors) {

    public GitHubCodeownersErrors {
        requireNonNull(errors, "errors");
    }
}
