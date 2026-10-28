package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The GitHub App installation that was registered.
 *
 * @param id The unique identifier of the GitHub App installation
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/organization-external-property-installation/properties/installation")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubOrganizationExternalPropertyInstallationInstallation(long id) {}
