package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Empty configuration used when pausing or resuming a Microsoft Agent 365 stream. The JSON value is always an empty
 * object.
 *
 * @param properties The empty object, written as the JSON value
 */
@CatalogSchema("operations/enterprise-admin/update-audit-log-stream/requestBody/properties/vendor_specific/oneOf/8")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubUpdateAuditLogStreamEmptyVendorSpecific(
        @JsonValue @Nullable Map<String, Object> properties) implements GitHubUpdateAuditLogStreamVendorSpecific {

    /** Empty configuration. */
    public GitHubUpdateAuditLogStreamEmptyVendorSpecific() {
        this(Map.of());
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public GitHubUpdateAuditLogStreamEmptyVendorSpecific {
        if (properties == null) {
            properties = Map.of();
        }
        if (!properties.isEmpty()) {
            throw new IllegalArgumentException("Microsoft Agent 365 configuration must be an empty object");
        }
        properties = Map.copyOf(properties);
    }
}
