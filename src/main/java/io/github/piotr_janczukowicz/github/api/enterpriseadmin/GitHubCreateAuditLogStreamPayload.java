package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Audit log stream to create for an enterprise.
 *
 * @param enabled Whether the stream is enabled
 * @param streamType The audit log streaming provider
 * @param vendorSpecific The configuration of the streaming provider
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/create-audit-log-stream/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubCreateAuditLogStreamPayload(
        boolean enabled,
        GitHubCreateAuditLogStreamType streamType,
        GitHubCreateAuditLogStreamVendorSpecific vendorSpecific) {

    public GitHubCreateAuditLogStreamPayload {
        requireNonNull(streamType, "streamType");
        requireNonNull(vendorSpecific, "vendorSpecific");
    }
}
