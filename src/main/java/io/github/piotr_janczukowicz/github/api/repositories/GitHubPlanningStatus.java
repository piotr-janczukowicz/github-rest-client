package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A planning status and its lifecycle category.
 *
 * @param id The stable identifier of the planning status.
 * @param name The display name of the planning status, which may change in the future.
 * @param category A stable identifier for the category, such as {@code backlog}, {@code planned}, {@code started},
 *     {@code completed}, {@code not_planned} or {@code duplicate}. Clients should expect new keys to be added.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/planning-status")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPlanningStatus(long id, String name, String category) {
    public GitHubPlanningStatus {
        requireNonNull(name, "name");
        requireNonNull(category, "category");
    }
}
