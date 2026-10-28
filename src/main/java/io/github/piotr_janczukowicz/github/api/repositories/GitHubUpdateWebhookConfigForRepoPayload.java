package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.net.URI;
import org.jspecify.annotations.Nullable;

/**
 * Update webhook configuration.
 *
 * @param url Url
 * @param contentType Content type
 * @param secret Secret
 * @param insecureSsl Insecure ssl
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/repos/update-webhook-config-for-repo/requestBody",
    "operations/orgs/update-webhook-config-for-org/requestBody"
})
public record GitHubUpdateWebhookConfigForRepoPayload(
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
        return "GitHubUpdateWebhookConfigForRepoPayload[url=" + url + ", contentType=" + contentType
                + ", secret=<redacted>, insecureSsl=" + insecureSsl + "]";
    }
}
