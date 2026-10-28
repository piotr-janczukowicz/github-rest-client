package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubWebhookConfigInsecureSsl;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.net.URI;
import org.jspecify.annotations.Nullable;

/**
 * Inline webhook configuration for {@link GitHubCreateOrgWebhookPayload}.
 *
 * @param url Url
 * @param contentType Content type
 * @param secret Secret
 * @param insecureSsl Insecure ssl
 * @param username Username
 * @param password Password
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/create-webhook/requestBody/properties/config")
public record GitHubCreateOrgWebhookConfig(
        URI url,
        @Nullable String contentType,
        @Nullable String secret,
        @Nullable GitHubWebhookConfigInsecureSsl insecureSsl,
        @Nullable String username,
        @Nullable String password) {

    public GitHubCreateOrgWebhookConfig {
        requireNonNull(url, "url");
    }

    /**
     * Describes this record without its secrets.
     *
     * @return text with {@code secret, password} redacted
     */
    @Override
    public String toString() {
        return "GitHubCreateOrgWebhookConfig[url=" + url + ", contentType=" + contentType
                + ", secret=<redacted>, insecureSsl=" + insecureSsl + ", username=" + username
                + ", password=<redacted>]";
    }
}
