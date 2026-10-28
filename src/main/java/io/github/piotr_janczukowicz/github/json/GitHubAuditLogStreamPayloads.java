package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubAmazonS3AccessKeysConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubAmazonS3OidcConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubAzureBlobConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubAzureHubConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubCreateAuditLogStreamPayload;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubCreateAuditLogStreamType;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubCreateAuditLogStreamVendorSpecific;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubDatadogConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubGoogleCloudConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubHecConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubSplunkConfig;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubUpdateAuditLogStreamEmptyVendorSpecific;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubUpdateAuditLogStreamPayload;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubUpdateAuditLogStreamType;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubUpdateAuditLogStreamVendorSpecific;
import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import java.util.Map;

/**
 * Shared variant ladder for the audit log stream request bodies. The sibling {@code stream_type} decides which record
 * {@code vendor_specific} is bound to; Amazon S3 is split by its {@code authentication_type}, and the Microsoft Agent
 * 365 update takes an empty object only.
 */
final class GitHubAuditLogStreamPayloads {

    private static final String ENABLED = "enabled";
    private static final String STREAM_TYPE = "stream_type";
    private static final String VENDOR_SPECIFIC = "vendor_specific";
    private static final String AUTHENTICATION_TYPE = "authentication_type";
    private static final String OIDC = "oidc";
    private static final String ACCESS_KEYS = "access_keys";

    private GitHubAuditLogStreamPayloads() {
        throw new UnsupportedOperationException("Utility class");
    }

    static GitHubCreateAuditLogStreamPayload readCreate(final BufferedJson json) {
        final boolean enabled = json.readRequiredField(ENABLED, Boolean.class);
        final GitHubCreateAuditLogStreamType streamType =
                json.requiredWire(STREAM_TYPE, GitHubCreateAuditLogStreamType::fromWire);
        final GitHubCreateAuditLogStreamVendorSpecific vendorSpecific =
                switch (streamType) {
                    case AZURE_BLOB_STORAGE -> json.readRequiredField(VENDOR_SPECIFIC, GitHubAzureBlobConfig.class);
                    case AZURE_EVENT_HUBS -> json.readRequiredField(VENDOR_SPECIFIC, GitHubAzureHubConfig.class);
                    case AMAZON_S3 ->
                        amazonS3(json, GitHubAmazonS3OidcConfig.class, GitHubAmazonS3AccessKeysConfig.class);
                    case SPLUNK -> json.readRequiredField(VENDOR_SPECIFIC, GitHubSplunkConfig.class);
                    case HTTPS_EVENT_COLLECTOR -> json.readRequiredField(VENDOR_SPECIFIC, GitHubHecConfig.class);
                    case GOOGLE_CLOUD_STORAGE -> json.readRequiredField(VENDOR_SPECIFIC, GitHubGoogleCloudConfig.class);
                    case DATADOG -> json.readRequiredField(VENDOR_SPECIFIC, GitHubDatadogConfig.class);
                };
        return new GitHubCreateAuditLogStreamPayload(enabled, streamType, vendorSpecific);
    }

    static GitHubUpdateAuditLogStreamPayload readUpdate(final BufferedJson json) {
        final boolean enabled = json.readRequiredField(ENABLED, Boolean.class);
        final GitHubUpdateAuditLogStreamType streamType =
                json.requiredWire(STREAM_TYPE, GitHubUpdateAuditLogStreamType::fromWire);
        final GitHubUpdateAuditLogStreamVendorSpecific vendorSpecific =
                switch (streamType) {
                    case AZURE_BLOB_STORAGE -> json.readRequiredField(VENDOR_SPECIFIC, GitHubAzureBlobConfig.class);
                    case AZURE_EVENT_HUBS -> json.readRequiredField(VENDOR_SPECIFIC, GitHubAzureHubConfig.class);
                    case AMAZON_S3 ->
                        amazonS3(json, GitHubAmazonS3OidcConfig.class, GitHubAmazonS3AccessKeysConfig.class);
                    case SPLUNK -> json.readRequiredField(VENDOR_SPECIFIC, GitHubSplunkConfig.class);
                    case HTTPS_EVENT_COLLECTOR -> json.readRequiredField(VENDOR_SPECIFIC, GitHubHecConfig.class);
                    case GOOGLE_CLOUD_STORAGE -> json.readRequiredField(VENDOR_SPECIFIC, GitHubGoogleCloudConfig.class);
                    case DATADOG -> json.readRequiredField(VENDOR_SPECIFIC, GitHubDatadogConfig.class);
                    case MICROSOFT_AGENT_365 -> microsoftAgent365(json);
                };
        return new GitHubUpdateAuditLogStreamPayload(enabled, streamType, vendorSpecific);
    }

    // The two Amazon S3 config records implement the create and the update variant alike; T is the one the caller
    // needs.
    private static <T> T amazonS3(
            final BufferedJson json, final Class<? extends T> oidc, final Class<? extends T> accessKeys) {
        final Class<? extends T> type = isAmazonS3Oidc(json) ? oidc : accessKeys;
        return json.readRequiredField(VENDOR_SPECIFIC, type);
    }

    private static GitHubUpdateAuditLogStreamVendorSpecific microsoftAgent365(final BufferedJson json) {
        final Map<?, ?> vendorSpecific = json.readRequiredField(VENDOR_SPECIFIC, Map.class);
        if (!vendorSpecific.isEmpty()) {
            throw json.mismatch("Microsoft Agent 365 vendor_specific must be an empty object");
        }
        return new GitHubUpdateAuditLogStreamEmptyVendorSpecific();
    }

    private static boolean isAmazonS3Oidc(final BufferedJson json) {
        final Map<?, ?> vendorSpecific = json.readRequiredField(VENDOR_SPECIFIC, Map.class);
        final Object authenticationType = vendorSpecific.get(AUTHENTICATION_TYPE);
        if (OIDC.equals(authenticationType)) {
            return true;
        }
        if (ACCESS_KEYS.equals(authenticationType)) {
            return false;
        }
        throw json.mismatch("Amazon S3 vendor_specific authentication_type must be oidc or access_keys");
    }
}
