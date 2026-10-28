package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Result of deleting a cost center.
 *
 * @param message A message indicating the result of the deletion operation.
 * @param id The unique identifier of the deleted cost center.
 * @param name The name of the deleted cost center.
 * @param costCenterState The state of the cost center after deletion.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/delete-cost-center")
@Flavors(GitHubFlavor.GHEC)
public record GitHubDeleteCostCenter(
        String message,
        String id,
        String name,
        @JsonProperty("costCenterState") GitHubDeleteCostCenterCostCenterState costCenterState) {
    public GitHubDeleteCostCenter {
        requireNonNull(message, "message");
        requireNonNull(id, "id");
        requireNonNull(name, "name");
        requireNonNull(costCenterState, "costCenterState");
    }
}
