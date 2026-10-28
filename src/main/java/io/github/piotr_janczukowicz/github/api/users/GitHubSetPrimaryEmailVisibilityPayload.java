package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Set Primary Email Visibility.
 *
 * @param visibility Denotes whether an email is publicly visible.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/users/set-primary-email-visibility-for-authenticated-user/requestBody")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetPrimaryEmailVisibilityPayload(GitHubEmailVisibility visibility) {

    public GitHubSetPrimaryEmailVisibilityPayload {
        requireNonNull(visibility, "visibility");
    }
}
