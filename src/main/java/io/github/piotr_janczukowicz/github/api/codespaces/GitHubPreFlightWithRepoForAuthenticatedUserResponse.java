package io.github.piotr_janczukowicz.github.api.codespaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Pre-flight information for creating a codespace in a repository.
 *
 * @param billableOwner The user who would be billed for the codespace.
 * @param defaults Default values for a new codespace in the repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/codespaces/pre-flight-with-repo-for-authenticated-user/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPreFlightWithRepoForAuthenticatedUserResponse(
        @Nullable GitHubSimpleUser billableOwner, @Nullable GitHubCodespaceDefaults defaults) {}
