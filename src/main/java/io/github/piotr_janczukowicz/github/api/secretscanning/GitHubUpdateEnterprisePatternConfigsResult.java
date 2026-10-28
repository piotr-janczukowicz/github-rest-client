package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update enterprise pattern configurations response.
 *
 * @param patternConfigVersion The updated pattern configuration version.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/secret-scanning/update-enterprise-pattern-configs/200")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubUpdateEnterprisePatternConfigsResult(
        @Nullable String patternConfigVersion) {}
