package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Self-hosted runner permissions of an enterprise.
 *
 * @param disableSelfHostedRunnersForAllOrgs When true, repository-level runners will be disabled across all
 *     organizations in the enterprise
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/get-self-hosted-runners-permissions/200")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubSelfHostedRunnersPermissionsForEnterprise(boolean disableSelfHostedRunnersForAllOrgs) {}
