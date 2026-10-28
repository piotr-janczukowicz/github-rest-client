package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Organization webhook configuration embedded in {@link GitHubOrgHook}.
 *
 * @param url Url
 * @param insecureSsl Insecure ssl
 * @param contentType Content type
 * @param secret Secret
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/org-hook/properties/config")
@RecordBuilder
public record GitHubOrgHookConfig(
        @Nullable String url,
        @Nullable String insecureSsl,
        @Nullable String contentType,
        @Nullable String secret) {
    /**
     * Describes this record without its secrets.
     *
     * @return text with {@code secret} redacted
     */
    @Override
    public String toString() {
        return "GitHubOrgHookConfig[url=" + url + ", insecureSsl=" + insecureSsl + ", contentType=" + contentType
                + ", secret=<redacted>]";
    }
}
