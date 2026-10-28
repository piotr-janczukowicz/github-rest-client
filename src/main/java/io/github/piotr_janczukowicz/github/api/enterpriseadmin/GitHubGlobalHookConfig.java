package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubGlobalHookConfig
 *
 * @param url url
 * @param contentType content type
 * @param insecureSsl insecure ssl
 * @param secret secret
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/global-hook/properties/config")
@Flavors(GitHubFlavor.GHES)
public record GitHubGlobalHookConfig(
        @Nullable String url,
        @Nullable String contentType,
        @Nullable String insecureSsl,
        @Nullable String secret) {
    /**
     * Describes this record without its secret.
     *
     * @return text with {@code secret} redacted
     */
    @Override
    public String toString() {
        return "GitHubGlobalHookConfig[" + "url=" + url + ", contentType=" + contentType + ", insecureSsl="
                + insecureSsl + ", secret=<redacted>" + "]";
    }
}
