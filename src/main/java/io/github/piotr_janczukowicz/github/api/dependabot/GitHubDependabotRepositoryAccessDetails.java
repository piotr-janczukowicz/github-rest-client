package io.github.piotr_janczukowicz.github.api.dependabot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Information about repositories that Dependabot is able to access in an organization.
 *
 * @param defaultLevel The default repository access level for Dependabot updates.
 * @param accessibleRepositories Accessible repositories
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-repository-access-details")
public record GitHubDependabotRepositoryAccessDetails(
        @Nullable GitHubDependabotRepositoryAccessDefaultLevel defaultLevel,
        @Nullable List<GitHubSimpleRepository> accessibleRepositories) {}
