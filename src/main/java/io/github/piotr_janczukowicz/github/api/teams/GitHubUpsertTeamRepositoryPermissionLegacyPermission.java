package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * **Closing down notice**. The permission that new repositories will be added to the team with when none is specified.
 */
@CatalogSchema("operations/teams/add-or-update-repo-permissions-legacy/requestBody/properties/permission")
public enum GitHubUpsertTeamRepositoryPermissionLegacyPermission {
    PULL("pull"),
    MODERATE("moderate"),
    PUSH("push"),
    ADMIN("admin");

    private static final EnumUtils.WireIndex<GitHubUpsertTeamRepositoryPermissionLegacyPermission> WIRE_INDEX =
            EnumUtils.index(
                    GitHubUpsertTeamRepositoryPermissionLegacyPermission.class,
                    GitHubUpsertTeamRepositoryPermissionLegacyPermission::wireValue);

    private final String wireValue;

    GitHubUpsertTeamRepositoryPermissionLegacyPermission(final String wireValue) {
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
     * Permission from a wire value.
     *
     * @param value JSON value
     * @return matching permission
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known permission
     */
    @JsonCreator
    public static GitHubUpsertTeamRepositoryPermissionLegacyPermission fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub upsert team repository permission legacy permission: " + value));
    }
}
