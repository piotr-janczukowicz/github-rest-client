package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Repository access selection of an installation.
 *
 * @param repositorySelection Whether all or only selected repositories are accessible
 * @param repositories The names of the repositories to grant access to
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-apps/change-installation-repository-access-selection/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubChangeRepositoryAccessSelectionPayload(
        GitHubChangeRepositoryAccessSelection repositorySelection,
        @Nullable List<String> repositories) {

    public GitHubChangeRepositoryAccessSelectionPayload {
        requireNonNull(repositorySelection, "repositorySelection");
        if (repositories != null) {
            repositories = List.copyOf(repositories);
        }
    }
}
