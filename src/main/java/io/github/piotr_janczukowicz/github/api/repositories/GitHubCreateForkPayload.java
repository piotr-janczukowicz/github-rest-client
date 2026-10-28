package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create fork.
 *
 * @param organization The organization name. When forking into an organization, the forking user must be a member.
 * @param name The name of the forked repository.
 * @param defaultBranchOnly When forking, fork only the default branch.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-fork/requestBody")
public record GitHubCreateForkPayload(
        @Nullable String organization,
        @Nullable String name,
        @Nullable Boolean defaultBranchOnly) {}
