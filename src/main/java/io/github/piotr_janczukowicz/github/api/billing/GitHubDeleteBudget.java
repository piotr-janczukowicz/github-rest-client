package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Result of deleting a budget.
 *
 * @param message A message indicating the result of the deletion operation
 * @param id The ID of the deleted budget
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/delete-budget")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubDeleteBudget(String message, String id) {

    public GitHubDeleteBudget {
        requireNonNull(message, "message");
        requireNonNull(id, "id");
    }
}
