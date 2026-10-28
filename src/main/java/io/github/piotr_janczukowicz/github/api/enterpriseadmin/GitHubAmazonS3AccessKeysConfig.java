package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Amazon S3 access keys configuration for audit log streaming.
 *
 * @param bucket Amazon S3 bucket name
 * @param region AWS S3 bucket region
 * @param keyId Key ID obtained from the audit log stream key endpoint used to encrypt secrets
 * @param authenticationType Authentication type for Amazon S3
 * @param encryptedSecretKey Encrypted AWS secret key
 * @param encryptedAccessKeyId Encrypted AWS access key ID
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/amazon-s3-access-keys-config")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubAmazonS3AccessKeysConfig(
        String bucket,
        String region,
        String keyId,
        GitHubAmazonS3AccessKeysConfigAuthenticationType authenticationType,
        String encryptedSecretKey,
        String encryptedAccessKeyId)
        implements GitHubCreateAuditLogStreamVendorSpecific, GitHubUpdateAuditLogStreamVendorSpecific {

    public GitHubAmazonS3AccessKeysConfig {
        requireNonNull(bucket, "bucket");
        requireNonNull(region, "region");
        requireNonNull(keyId, "keyId");
        requireNonNull(authenticationType, "authenticationType");
        requireNonNull(encryptedSecretKey, "encryptedSecretKey");
        requireNonNull(encryptedAccessKeyId, "encryptedAccessKeyId");
    }

    /**
     * Describes this record without its secrets.
     *
     * @return text with {@code encryptedSecretKey} and {@code encryptedAccessKeyId} redacted
     */
    @Override
    public String toString() {
        return "GitHubAmazonS3AccessKeysConfig[bucket=" + bucket + ", region=" + region + ", keyId=" + keyId
                + ", authenticationType=" + authenticationType + ", encryptedSecretKey=<redacted>"
                + ", encryptedAccessKeyId=<redacted>]";
    }
}
