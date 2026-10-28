package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * HTTPS Event Collector configuration for audit log streaming.
 *
 * @param domain Domain of the HTTPS Event Collector instance
 * @param port The port number for connecting to the HTTPS Event Collector
 * @param keyId Key ID obtained from the audit log stream key endpoint used to encrypt secrets
 * @param encryptedToken Encrypted HTTPS Event Collector token
 * @param path The path of the HTTPS Event Collector endpoint
 * @param sslVerify Whether SSL certificates are verified
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/hec-config")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubHecConfig(
        String domain, int port, String keyId, String encryptedToken, String path, boolean sslVerify)
        implements GitHubCreateAuditLogStreamVendorSpecific, GitHubUpdateAuditLogStreamVendorSpecific {

    public GitHubHecConfig {
        requireNonNull(domain, "domain");
        requireNonNull(keyId, "keyId");
        requireNonNull(encryptedToken, "encryptedToken");
        requireNonNull(path, "path");
    }

    /**
     * Describes this record without its secret.
     *
     * @return text with {@code encryptedToken} redacted
     */
    @Override
    public String toString() {
        return "GitHubHecConfig[domain=" + domain + ", port=" + port + ", keyId=" + keyId
                + ", encryptedToken=<redacted>, path=" + path + ", sslVerify=" + sslVerify + "]";
    }
}
