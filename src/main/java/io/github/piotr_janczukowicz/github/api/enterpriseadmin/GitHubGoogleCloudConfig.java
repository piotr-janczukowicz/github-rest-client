package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Google Cloud Storage configuration for audit log streaming.
 *
 * @param bucket The name of the Google Cloud Storage bucket
 * @param keyId Key ID obtained from the audit log stream key endpoint used to encrypt secrets
 * @param encryptedJsonCredentials Encrypted JSON credentials of the Google Cloud service account
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/google-cloud-config")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubGoogleCloudConfig(String bucket, String keyId, String encryptedJsonCredentials)
        implements GitHubCreateAuditLogStreamVendorSpecific, GitHubUpdateAuditLogStreamVendorSpecific {

    public GitHubGoogleCloudConfig {
        requireNonNull(bucket, "bucket");
        requireNonNull(keyId, "keyId");
        requireNonNull(encryptedJsonCredentials, "encryptedJsonCredentials");
    }

    /**
     * Describes this record without its secret.
     *
     * @return text with {@code encryptedJsonCredentials} redacted
     */
    @Override
    public String toString() {
        return "GitHubGoogleCloudConfig[bucket=" + bucket + ", keyId=" + keyId
                + ", encryptedJsonCredentials=<redacted>]";
    }
}
