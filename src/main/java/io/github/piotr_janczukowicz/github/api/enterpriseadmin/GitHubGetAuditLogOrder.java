package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Order of the audit log entries. */
@CatalogSchema("operations/enterprise-admin/get-audit-log/parameters/5")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubGetAuditLogOrder {
    /** Desc. */
    DESC("desc"),
    /** Asc. */
    ASC("asc");

    private static final EnumUtils.WireIndex<GitHubGetAuditLogOrder> WIRE_INDEX =
            EnumUtils.index(GitHubGetAuditLogOrder.class, GitHubGetAuditLogOrder::wireValue);

    private final String wireValue;

    GitHubGetAuditLogOrder(final String wireValue) {
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
    public static GitHubGetAuditLogOrder fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub get audit log order: " + value));
    }
}
