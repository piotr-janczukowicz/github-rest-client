package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubInstallation;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List of installations for the authenticated user.
 *
 * @param totalCount Total count
 * @param installations Installations
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/apps/list-installations-for-authenticated-user/200")
public record GitHubListInstallationsForAuthenticatedUserPage(int totalCount, List<GitHubInstallation> installations) {

    public GitHubListInstallationsForAuthenticatedUserPage {
        requireNonNull(installations, "installations");
    }
}
