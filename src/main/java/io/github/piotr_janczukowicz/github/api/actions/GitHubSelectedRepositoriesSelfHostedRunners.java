package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Repositories allowed to use repository-level self-hosted runners in an organization.
 *
 * @param totalCount Total number of repositories
 * @param repositories The repositories
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/list-selected-repositories-self-hosted-runners-organization/200")
public record GitHubSelectedRepositoriesSelfHostedRunners(
        @Nullable Integer totalCount, @Nullable List<GitHubRepository> repositories) {}
