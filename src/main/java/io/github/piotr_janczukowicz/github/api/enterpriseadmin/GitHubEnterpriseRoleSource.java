package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Source answers the question, "where did this role come from?" */
@CatalogSchema("components/schemas/enterprise-role/properties/source")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubEnterpriseRoleSource {
    /** Enterprise. */
    ENTERPRISE("Enterprise"),
    /** Predefined. */
    PREDEFINED("Predefined"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubEnterpriseRoleSource> WIRE_INDEX =
            EnumUtils.index(GitHubEnterpriseRoleSource.class, GitHubEnterpriseRoleSource::wireValue);

    private final String wireValue;

    GitHubEnterpriseRoleSource(final String wireValue) {
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
     * Enterprise role source from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubEnterpriseRoleSource fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
