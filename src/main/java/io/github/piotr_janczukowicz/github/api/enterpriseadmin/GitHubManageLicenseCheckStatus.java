package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The license status of the currently active enterprise license. */
@CatalogSchema("components/schemas/ghes-license-check/properties/status")
@Flavors(GitHubFlavor.GHES)
public enum GitHubManageLicenseCheckStatus {
    VALID("valid"),
    INVALID("invalid"),
    EXPIRED("expired"),
    CLUSTER_MODE_NOT_SUPPORTED("cluster mode not supported"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubManageLicenseCheckStatus> WIRE_INDEX =
            EnumUtils.index(GitHubManageLicenseCheckStatus.class, GitHubManageLicenseCheckStatus::wireValue);

    private final String wireValue;

    GitHubManageLicenseCheckStatus(final String wireValue) {
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
     * status from a wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubManageLicenseCheckStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
