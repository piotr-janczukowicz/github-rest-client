package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of advisory. */
@CatalogSchema({
    "components/schemas/global-advisory/properties/type",
    "operations/security-advisories/list-global-advisories/parameters/1"
})
public enum GitHubGlobalAdvisoryType {
    REVIEWED("reviewed"),
    UNREVIEWED("unreviewed"),
    MALWARE("malware");

    private static final EnumUtils.WireIndex<GitHubGlobalAdvisoryType> WIRE_INDEX =
            EnumUtils.index(GitHubGlobalAdvisoryType.class, GitHubGlobalAdvisoryType::wireValue);

    private final String wireValue;

    GitHubGlobalAdvisoryType(final String wireValue) {
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
     * Resolves global advisory type from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubGlobalAdvisoryType
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known global advisory type
     */
    @JsonCreator
    public static GitHubGlobalAdvisoryType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub global advisory type: " + value));
    }
}
