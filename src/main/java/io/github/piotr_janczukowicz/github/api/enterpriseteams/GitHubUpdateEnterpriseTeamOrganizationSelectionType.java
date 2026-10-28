package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Specifies which organizations in the enterprise should have access to this team. */
@CatalogSchema("operations/enterprise-teams/update/requestBody/properties/organization_selection_type")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubUpdateEnterpriseTeamOrganizationSelectionType {
    /** The team is not assigned to any organizations. This is the default when you create a new team. */
    DISABLED("disabled"),
    /** The team is assigned to specific organizations. */
    SELECTED("selected"),
    /** The team is assigned to all current and future organizations in the enterprise. */
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubUpdateEnterpriseTeamOrganizationSelectionType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubUpdateEnterpriseTeamOrganizationSelectionType.class,
                    GitHubUpdateEnterpriseTeamOrganizationSelectionType::wireValue);

    private final String wireValue;

    GitHubUpdateEnterpriseTeamOrganizationSelectionType(final String wireValue) {
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
     * Organization selection type from a wire value.
     *
     * @param value JSON value
     * @return matching selection type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known selection type
     */
    @JsonCreator
    public static GitHubUpdateEnterpriseTeamOrganizationSelectionType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub update enterprise team organization selection type: " + value));
    }
}
