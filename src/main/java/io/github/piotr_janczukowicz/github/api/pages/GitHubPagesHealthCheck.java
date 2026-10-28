package io.github.piotr_janczukowicz.github.api.pages;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Pages Health Check Status
 *
 * @param domain Domain
 * @param altDomain Alt domain
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/pages-health-check")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPagesHealthCheck(
        @Nullable GitHubPagesHealthCheckDomain domain,
        @Nullable GitHubPagesHealthCheckDomain altDomain) {}
