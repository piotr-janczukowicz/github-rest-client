package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Retired: this field is no longer supported. */
@CatalogSchema("operations/enterprise-teams/create/requestBody/properties/sync_to_organizations")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubCreateEnterpriseTeamSyncToOrganizations {
    /** All. */
    ALL("all"),
    /** Disabled. */
    DISABLED("disabled");

    private static final EnumUtils.WireIndex<GitHubCreateEnterpriseTeamSyncToOrganizations> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCreateEnterpriseTeamSyncToOrganizations.class,
                    GitHubCreateEnterpriseTeamSyncToOrganizations::wireValue);

    private final String wireValue;

    GitHubCreateEnterpriseTeamSyncToOrganizations(final String wireValue) {
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
     * Sync setting from a wire value.
     *
     * @param value JSON value
     * @return matching setting
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known setting
     */
    @JsonCreator
    public static GitHubCreateEnterpriseTeamSyncToOrganizations fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub create enterprise team sync to organizations: " + value));
    }
}
