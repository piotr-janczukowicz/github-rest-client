package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Vendor-specific configuration of an audit log stream. The sibling {@code stream_type} selects the record when a
 * request body is decoded.
 */
@CatalogSchema("operations/enterprise-admin/update-audit-log-stream/requestBody/properties/vendor_specific")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public sealed interface GitHubUpdateAuditLogStreamVendorSpecific
        permits GitHubAzureBlobConfig,
                GitHubAzureHubConfig,
                GitHubAmazonS3OidcConfig,
                GitHubAmazonS3AccessKeysConfig,
                GitHubSplunkConfig,
                GitHubHecConfig,
                GitHubGoogleCloudConfig,
                GitHubDatadogConfig,
                GitHubUpdateAuditLogStreamEmptyVendorSpecific {}
