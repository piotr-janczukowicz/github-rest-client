package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Repository Enterprise Stats
 *
 * @param totalRepos total repos
 * @param rootRepos root repos
 * @param forkRepos fork repos
 * @param orgRepos org repos
 * @param totalPushes total pushes
 * @param totalWikis total wikis
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-overview/properties/repos")
@Flavors(GitHubFlavor.GHES)
public record GitHubEnterpriseOverviewRepos(
        int totalRepos, int rootRepos, int forkRepos, int orgRepos, int totalPushes, int totalWikis) {}
