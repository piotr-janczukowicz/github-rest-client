package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Hooks Enterprise Stats
 *
 * @param totalHooks total hooks
 * @param activeHooks active hooks
 * @param inactiveHooks inactive hooks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-hook-overview")
@Flavors(GitHubFlavor.GHES)
public record GitHubEnterpriseHookOverview(int totalHooks, int activeHooks, int inactiveHooks) {}
