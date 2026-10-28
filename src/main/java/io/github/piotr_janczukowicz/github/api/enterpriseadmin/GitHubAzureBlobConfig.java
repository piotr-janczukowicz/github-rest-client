package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Azure Blob Storage configuration for audit log streaming.
 *
 * @param keyId Key ID obtained from the audit log stream key endpoint used to encrypt secrets
 * @param encryptedSasUrl Encrypted shared access signature URL
 * @param container The name of the Azure Blob Storage container to which the audit logs will be sent
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/azure-blob-config")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubAzureBlobConfig(String keyId, String encryptedSasUrl, String container)
        implements GitHubCreateAuditLogStreamVendorSpecific, GitHubUpdateAuditLogStreamVendorSpecific {

    public GitHubAzureBlobConfig {
        requireNonNull(keyId, "keyId");
        requireNonNull(encryptedSasUrl, "encryptedSasUrl");
        requireNonNull(container, "container");
    }

    /**
     * Describes this record without its secret.
     *
     * @return text with {@code encryptedSasUrl} redacted
     */
    @Override
    public String toString() {
        return "GitHubAzureBlobConfig[keyId=" + keyId + ", encryptedSasUrl=<redacted>, container=" + container + "]";
    }
}
