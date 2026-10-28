package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.EXTERNAL_PROPERTIES_DISPLAY_NAME;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Register an app installation for external custom properties request body.
 *
 * @param installationId The unique identifier of the GitHub App installation to register for managing external custom
 *     properties. When authenticating as a GitHub App installation, this defaults to the authenticated installation and
 *     can be omitted. It is required for all other callers (users and fine-grained personal access tokens)
 * @param displayName The display name for this app installation's external custom properties in the organization. Must
 *     be 1 to 15 characters and contain only letters and numbers. Capitalization is preserved as entered. This can't be
 *     changed after the app installation is registered
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/external-properties-for-repos-register-organization-app-installation/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubExternalPropertiesRegisterInstallationPayload(
        @Nullable Long installationId, String displayName) {

    private static final String PARAM_DISPLAY_NAME = "displayName";

    public GitHubExternalPropertiesRegisterInstallationPayload {
        requireNonNull(displayName, PARAM_DISPLAY_NAME);
        displayName = requireMatches(
                requireMaxLength(requireMinLength(displayName, 1, PARAM_DISPLAY_NAME), 15, PARAM_DISPLAY_NAME),
                EXTERNAL_PROPERTIES_DISPLAY_NAME,
                PARAM_DISPLAY_NAME);
    }
}
