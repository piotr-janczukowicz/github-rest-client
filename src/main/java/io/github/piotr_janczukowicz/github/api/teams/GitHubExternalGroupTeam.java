package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A team linked to an external group.
 *
 * @param teamId The id for a team
 * @param teamName The name of the team
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/external-group/properties/teams/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubExternalGroupTeam(long teamId, String teamName) {

    public GitHubExternalGroupTeam {
        requireNonNull(teamName, "teamName");
    }
}
