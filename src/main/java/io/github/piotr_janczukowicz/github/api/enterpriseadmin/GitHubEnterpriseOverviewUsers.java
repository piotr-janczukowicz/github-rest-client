package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Enterprise User Stats
 *
 * @param totalUsers total users
 * @param adminUsers admin users
 * @param suspendedUsers suspended users
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-overview/properties/users")
@Flavors(GitHubFlavor.GHES)
public record GitHubEnterpriseOverviewUsers(int totalUsers, int adminUsers, int suspendedUsers) {}
