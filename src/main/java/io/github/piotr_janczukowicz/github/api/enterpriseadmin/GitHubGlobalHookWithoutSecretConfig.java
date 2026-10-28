package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubGlobalHookWithoutSecretConfig
 *
 * @param url url
 * @param contentType content type
 * @param insecureSsl insecure ssl
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/global-hook-2/properties/config")
@Flavors(GitHubFlavor.GHES)
public record GitHubGlobalHookWithoutSecretConfig(
        @Nullable String url,
        @Nullable String contentType,
        @Nullable String insecureSsl) {}
