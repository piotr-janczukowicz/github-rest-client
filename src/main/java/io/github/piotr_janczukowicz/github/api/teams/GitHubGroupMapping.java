package io.github.piotr_janczukowicz.github.api.teams;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * External groups to be mapped to a team for membership.
 *
 * @param groups Array of groups to be mapped to this team
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/group-mapping")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGroupMapping(@Nullable List<GitHubGroupMappingGroup> groups) {}
