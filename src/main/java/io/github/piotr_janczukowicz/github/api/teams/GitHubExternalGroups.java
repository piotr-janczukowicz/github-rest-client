package io.github.piotr_janczukowicz.github.api.teams;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A list of external groups available to be connected to a team.
 *
 * @param groups An array of external groups available to be mapped to a team
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/external-groups")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubExternalGroups(@Nullable List<GitHubExternalGroupsGroup> groups) {}
