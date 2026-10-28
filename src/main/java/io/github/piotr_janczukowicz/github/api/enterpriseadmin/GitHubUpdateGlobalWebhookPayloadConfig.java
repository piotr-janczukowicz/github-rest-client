package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Key/value pairs to provide settings for this webhook.
 *
 * @param url The URL to which the payloads will be delivered.
 * @param contentType The media type used to serialize the payloads. Supported values include `json` and `form`. The
 *     default is `form`.
 * @param secret If provided, the `secret` will be used as the `key` to generate the HMAC hex digest value in the
 *     [`X-Hub-Signature`](https://docs.github.com/enterprise-server@3.22/webhooks/event-payloads/#delivery-headers)
 *     header.
 * @param insecureSsl Determines whether the SSL certificate of the host for `url` will be verified when delivering
 *     payloads. Supported values include `0` (verification is performed) and `1` (verification is not performed). The
 *     default is `0`. **We strongly recommend not setting this to `1` as you are subject to man-in-the-middle and other
 *     attacks.**
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-global-webhook/requestBody/properties/config")
@Flavors(GitHubFlavor.GHES)
public record GitHubUpdateGlobalWebhookPayloadConfig(
        String url,
        @Nullable String contentType,
        @Nullable String secret,
        @Nullable String insecureSsl) {
    public GitHubUpdateGlobalWebhookPayloadConfig {
        requireNonNull(url, "url");
    }

    /**
     * Describes this record without its secret.
     *
     * @return text with {@code secret} redacted
     */
    @Override
    public String toString() {
        return "GitHubUpdateGlobalWebhookPayloadConfig[" + "url=" + url + ", contentType=" + contentType
                + ", secret=<redacted>" + ", insecureSsl=" + insecureSsl + "]";
    }
}
