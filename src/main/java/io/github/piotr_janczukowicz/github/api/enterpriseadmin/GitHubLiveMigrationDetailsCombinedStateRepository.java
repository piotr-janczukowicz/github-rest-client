package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubLiveMigrationDetailsCombinedStateRepository
 *
 * @param repositoryNwo repository nwo
 * @param phase phase
 * @param displayStatus display status
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/enterprise-admin/live-migration-get/200/properties/combined_state/properties/repositories/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationDetailsCombinedStateRepository(
        String repositoryNwo, @Nullable String phase, String displayStatus) {
    public GitHubLiveMigrationDetailsCombinedStateRepository {
        requireNonNull(repositoryNwo, "repositoryNwo");
        requireNonNull(displayStatus, "displayStatus");
    }
}
