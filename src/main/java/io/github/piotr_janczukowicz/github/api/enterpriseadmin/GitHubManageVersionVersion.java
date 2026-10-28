package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageVersionVersion
 *
 * @param version version
 * @param platform platform
 * @param buildId build id
 * @param buildDate build date
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-version/items/properties/version")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageVersionVersion(
        @Nullable String version,
        @Nullable GitHubManageVersionVersionPlatform platform,
        @Nullable String buildId,
        @Nullable String buildDate) {}
