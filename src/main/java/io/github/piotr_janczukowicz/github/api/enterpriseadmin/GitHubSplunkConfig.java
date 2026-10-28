package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Splunk configuration for audit log streaming.
 *
 * @param domain Domain of the Splunk instance
 * @param port The port number for connecting to Splunk
 * @param keyId Key ID obtained from the audit log stream key endpoint used to encrypt secrets
 * @param encryptedToken Encrypted Splunk token
 * @param sslVerify Whether SSL certificates are verified
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/splunk-config")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubSplunkConfig(String domain, int port, String keyId, String encryptedToken, boolean sslVerify)
        implements GitHubCreateAuditLogStreamVendorSpecific, GitHubUpdateAuditLogStreamVendorSpecific {

    public GitHubSplunkConfig {
        requireNonNull(domain, "domain");
        requireNonNull(keyId, "keyId");
        requireNonNull(encryptedToken, "encryptedToken");
    }

    /**
     * Describes this record without its secret.
     *
     * @return text with {@code encryptedToken} redacted
     */
    @Override
    public String toString() {
        return "GitHubSplunkConfig[domain=" + domain + ", port=" + port + ", keyId=" + keyId
                + ", encryptedToken=<redacted>, sslVerify=" + sslVerify + "]";
    }
}
