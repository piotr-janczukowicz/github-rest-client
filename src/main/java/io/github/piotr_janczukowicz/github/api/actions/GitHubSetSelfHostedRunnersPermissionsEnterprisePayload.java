package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Set self-hosted runner permissions for an enterprise.
 *
 * @param disableSelfHostedRunnersForAllOrgs When true, repository-level runners will be disabled across all
 *     organizations in the enterprise
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/set-self-hosted-runners-permissions/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubSetSelfHostedRunnersPermissionsEnterprisePayload(boolean disableSelfHostedRunnersForAllOrgs) {}
