package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Plan.
 *
 * @param name Name
 * @param space Space
 * @param privateRepos Private repos
 * @param filledSeats Filled seats
 * @param seats Seats
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/team-organization/properties/plan")
@RecordBuilder
public record GitHubTeamOrganizationPlan(
        String name,
        long space,
        long privateRepos,
        @Nullable Long filledSeats,
        @Nullable Long seats) {

    public GitHubTeamOrganizationPlan {
        requireNonNull(name, "name");
    }
}
