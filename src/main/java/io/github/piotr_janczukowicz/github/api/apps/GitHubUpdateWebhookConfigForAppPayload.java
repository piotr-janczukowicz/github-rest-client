package io.github.piotr_janczukowicz.github.api.apps;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubWebhookConfigInsecureSsl;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.net.URI;
import org.jspecify.annotations.Nullable;

/**
 * Update the webhook configuration for a GitHub App.
 *
 * @param url Url
 * @param contentType Content type
 * @param secret Secret
 * @param insecureSsl Insecure ssl
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/apps/update-webhook-config-for-app/requestBody")
public record GitHubUpdateWebhookConfigForAppPayload(
        @Nullable URI url,
        @Nullable String contentType,
        @Nullable String secret,
        @Nullable GitHubWebhookConfigInsecureSsl insecureSsl) {

    /**
     * Describes this payload without the signing secret.
     *
     * @return text with {@code secret} redacted
     */
    @Override
    public String toString() {
        return "GitHubUpdateWebhookConfigForAppPayload[url=" + url + ", contentType=" + contentType
                + ", secret=<redacted>, insecureSsl=" + insecureSsl + "]";
    }
}
