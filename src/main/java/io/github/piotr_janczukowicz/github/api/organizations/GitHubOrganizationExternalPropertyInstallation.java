package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A GitHub App installation that has been registered to read and write an organization's external custom properties,
 * together with the display name assigned to it.
 *
 * @param displayName The display name assigned to the app installation's external custom properties in the organization
 * @param installation The GitHub App installation that was registered
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/organization-external-property-installation")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubOrganizationExternalPropertyInstallation(
        String displayName, GitHubOrganizationExternalPropertyInstallationInstallation installation) {

    private static final String PARAM_DISPLAY_NAME = "displayName";
    private static final String PARAM_INSTALLATION = "installation";

    public GitHubOrganizationExternalPropertyInstallation {
        requireNonNull(displayName, PARAM_DISPLAY_NAME);
        requireNonNull(installation, PARAM_INSTALLATION);
    }
}
