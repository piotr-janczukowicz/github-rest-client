package io.github.piotr_janczukowicz.github.api.teams;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Team through which a Copilot seat assignee is granted access ({@code oneOf} team vs enterprise team). Jackson
 * deduction selects the implementing record from the JSON object shape.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({@JsonSubTypes.Type(GitHubTeam.class), @JsonSubTypes.Type(GitHubEnterpriseTeam.class)})
@CatalogSchema("components/schemas/copilot-seat-details/properties/assigning_team")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public sealed interface GitHubCopilotAssigningTeam permits GitHubTeam, GitHubEnterpriseTeam {}
