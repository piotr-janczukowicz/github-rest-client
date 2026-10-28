package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Type of audit log stream to update. */
@CatalogSchema("operations/enterprise-admin/update-audit-log-stream/requestBody/properties/stream_type")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubUpdateAuditLogStreamType {
    /** Azure Blob Storage. */
    AZURE_BLOB_STORAGE("Azure Blob Storage"),
    /** Azure Event Hubs. */
    AZURE_EVENT_HUBS("Azure Event Hubs"),
    /** Amazon S3. */
    AMAZON_S3("Amazon S3"),
    /** Splunk. */
    SPLUNK("Splunk"),
    /** HTTPS Event Collector. */
    HTTPS_EVENT_COLLECTOR("HTTPS Event Collector"),
    /** Google Cloud Storage. */
    GOOGLE_CLOUD_STORAGE("Google Cloud Storage"),
    /** Datadog. */
    DATADOG("Datadog"),
    /** Microsoft Agent 365. */
    MICROSOFT_AGENT_365("Microsoft Agent 365");

    private static final EnumUtils.WireIndex<GitHubUpdateAuditLogStreamType> WIRE_INDEX =
            EnumUtils.index(GitHubUpdateAuditLogStreamType.class, GitHubUpdateAuditLogStreamType::wireValue);

    private final String wireValue;

    GitHubUpdateAuditLogStreamType(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Value from a wire string.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubUpdateAuditLogStreamType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub update audit log stream type: " + value));
    }
}
