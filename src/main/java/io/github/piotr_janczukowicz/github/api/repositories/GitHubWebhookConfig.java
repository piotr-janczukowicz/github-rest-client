package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Webhook configuration.
 *
 * @param url Url
 * @param contentType Content type
 * @param secret Secret
 * @param insecureSsl Insecure ssl
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/webhook-config")
@RecordBuilder
public record GitHubWebhookConfig(
        @Nullable String url,
        @Nullable String contentType,
        @Nullable String secret,
        @Nullable GitHubWebhookConfigInsecureSsl insecureSsl) {

    /**
     * Describes this webhook configuration without the signing secret.
     *
     * @return text with {@code secret} redacted
     */
    @Override
    public String toString() {
        return "GitHubWebhookConfig[url=" + url + ", contentType=" + contentType + ", secret=<redacted>, insecureSsl="
                + insecureSsl + "]";
    }
}
