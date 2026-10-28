package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Packages metrics that are included in the server statistics payload.
 *
 * @param registryEnabled Whether GitHub Packages is enabled globally
 * @param registryV2Enabled Whether a beta registry is enabled
 * @param ecosystems The package ecosystems
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics-packages")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatisticsPackages(
        @Nullable Boolean registryEnabled,
        @Nullable Boolean registryV2Enabled,
        @Nullable List<GitHubServerStatisticsPackagesEcosystem> ecosystems) {}
