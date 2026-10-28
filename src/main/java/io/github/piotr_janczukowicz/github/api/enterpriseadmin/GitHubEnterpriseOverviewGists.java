package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Enterprise Gist Stats
 *
 * @param totalGists total gists
 * @param privateGists private gists
 * @param publicGists public gists
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-overview/properties/gists")
@Flavors(GitHubFlavor.GHES)
public record GitHubEnterpriseOverviewGists(int totalGists, int privateGists, int publicGists) {}
