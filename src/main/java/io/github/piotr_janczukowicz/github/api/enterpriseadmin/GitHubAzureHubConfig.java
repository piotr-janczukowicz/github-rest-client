package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Azure Event Hubs configuration for audit log streaming.
 *
 * @param name Instance name of Azure Event Hubs
 * @param encryptedConnstring Encrypted connection string for Azure Event Hubs
 * @param keyId Key ID obtained from the audit log stream key endpoint used to encrypt secrets
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/azure-hub-config")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubAzureHubConfig(String name, String encryptedConnstring, String keyId)
        implements GitHubCreateAuditLogStreamVendorSpecific, GitHubUpdateAuditLogStreamVendorSpecific {

    public GitHubAzureHubConfig {
        requireNonNull(name, "name");
        requireNonNull(encryptedConnstring, "encryptedConnstring");
        requireNonNull(keyId, "keyId");
    }

    /**
     * Describes this record without its secret.
     *
     * @return text with {@code encryptedConnstring} redacted
     */
    @Override
    public String toString() {
        return "GitHubAzureHubConfig[name=" + name + ", encryptedConnstring=<redacted>, keyId=" + keyId + "]";
    }
}
