package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Datadog configuration for audit log streaming.
 *
 * @param encryptedToken Encrypted Datadog token
 * @param site The Datadog site
 * @param keyId Key ID obtained from the audit log stream key endpoint used to encrypt secrets
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/datadog-config")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubDatadogConfig(String encryptedToken, GitHubDatadogConfigSite site, String keyId)
        implements GitHubCreateAuditLogStreamVendorSpecific, GitHubUpdateAuditLogStreamVendorSpecific {

    public GitHubDatadogConfig {
        requireNonNull(encryptedToken, "encryptedToken");
        requireNonNull(site, "site");
        requireNonNull(keyId, "keyId");
    }

    /**
     * Describes this record without its secret.
     *
     * @return text with {@code encryptedToken} redacted
     */
    @Override
    public String toString() {
        return "GitHubDatadogConfig[encryptedToken=<redacted>, site=" + site + ", keyId=" + keyId + "]";
    }
}
