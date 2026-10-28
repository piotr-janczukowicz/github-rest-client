package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Members allowed repository creation type. */
@CatalogSchema("operations/orgs/update/requestBody/properties/members_allowed_repository_creation_type")
public enum GitHubOrganizationMembersAllowedRepositoryCreationType {
    ALL("all"),
    PRIVATE("private"),
    NONE("none");

    private static final EnumUtils.WireIndex<GitHubOrganizationMembersAllowedRepositoryCreationType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubOrganizationMembersAllowedRepositoryCreationType.class,
                    GitHubOrganizationMembersAllowedRepositoryCreationType::wireValue);

    private final String wireValue;

    GitHubOrganizationMembersAllowedRepositoryCreationType(final String wireValue) {
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
     * Repository creation type from a wire value.
     *
     * @param value JSON value
     * @return matching creation type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known creation type
     */
    @JsonCreator
    public static GitHubOrganizationMembersAllowedRepositoryCreationType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub organization members allowed repository creation type: " + value));
    }
}
