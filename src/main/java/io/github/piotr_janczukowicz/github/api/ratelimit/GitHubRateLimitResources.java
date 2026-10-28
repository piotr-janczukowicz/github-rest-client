package io.github.piotr_janczukowicz.github.api.ratelimit;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Resources.
 *
 * @param core Core
 * @param search Search
 * @param graphql Graphql
 * @param codeSearch Code search
 * @param sourceImport Source import
 * @param integrationManifest Integration manifest
 * @param actionsRunnerRegistration Actions runner registration
 * @param scim Scim
 * @param dependencySnapshots Dependency snapshots
 * @param dependencySbom Dependency sbom
 * @param codeScanningAutofix Code scanning autofix
 * @param copilotUsageRecords Copilot usage records
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/rate-limit-overview/properties/resources")
public record GitHubRateLimitResources(
        GitHubRateLimit core,
        GitHubRateLimit search,
        @Nullable GitHubRateLimit graphql,
        @Nullable GitHubRateLimit codeSearch,
        @Nullable GitHubRateLimit sourceImport,
        @Nullable GitHubRateLimit integrationManifest,
        @Nullable GitHubRateLimit actionsRunnerRegistration,
        @Nullable GitHubRateLimit scim,
        @Nullable GitHubRateLimit dependencySnapshots,
        @Nullable GitHubRateLimit dependencySbom,
        @Nullable GitHubRateLimit codeScanningAutofix,
        @Nullable GitHubRateLimit copilotUsageRecords) {

    public GitHubRateLimitResources {
        requireNonNull(core, "core");
        requireNonNull(search, "search");
    }
}
