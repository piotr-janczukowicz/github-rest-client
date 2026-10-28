package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Amazon S3 OIDC configuration for audit log streaming.
 *
 * @param bucket Amazon S3 bucket name
 * @param region AWS S3 bucket region
 * @param keyId Key ID obtained from the audit log stream key endpoint used to encrypt secrets
 * @param authenticationType Authentication type for Amazon S3
 * @param arnRole The ARN of the role to assume
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/amazon-s3-oidc-config")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubAmazonS3OidcConfig(
        String bucket,
        String region,
        String keyId,
        GitHubAmazonS3OidcConfigAuthenticationType authenticationType,
        String arnRole)
        implements GitHubCreateAuditLogStreamVendorSpecific, GitHubUpdateAuditLogStreamVendorSpecific {

    public GitHubAmazonS3OidcConfig {
        requireNonNull(bucket, "bucket");
        requireNonNull(region, "region");
        requireNonNull(keyId, "keyId");
        requireNonNull(authenticationType, "authenticationType");
        requireNonNull(arnRole, "arnRole");
    }
}
