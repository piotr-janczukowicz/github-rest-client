package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubWebhookConfigInsecureSsl;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.net.URI;
import org.jspecify.annotations.Nullable;

/**
 * Inline webhook configuration for {@link GitHubUpdateOrgWebhookPayload}.
 *
 * @param url Url
 * @param contentType Content type
 * @param secret Secret
 * @param insecureSsl Insecure ssl
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/update-webhook/requestBody/properties/config")
public record GitHubUpdateOrgWebhookConfig(
        URI url,
        @Nullable String contentType,
        @Nullable String secret,
        @Nullable GitHubWebhookConfigInsecureSsl insecureSsl) {

    public GitHubUpdateOrgWebhookConfig {
        requireNonNull(url, "url");
    }

    /**
     * Describes this record without its secrets.
     *
     * @return text with {@code secret} redacted
     */
    @Override
    public String toString() {
        return "GitHubUpdateOrgWebhookConfig[url=" + url + ", contentType=" + contentType
                + ", secret=<redacted>, insecureSsl=" + insecureSsl + "]";
    }
}
