package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Query enum for {@code GET /orgs/{org}/members} {@code filter}
 * ({@code 2fa_disabled}/{@code 2fa_insecure}/{@code all}).
 *
 * <p>Distinct from {@link GitHubOrganizationsMembersFilter}, the query-parameter record that may include this enum.
 */
@CatalogSchema("operations/orgs/list-members/parameters/1")
public enum GitHubOrganizationMembersKind {
    TWO_FA_DISABLED("2fa_disabled"),
    TWO_FA_INSECURE("2fa_insecure"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubOrganizationMembersKind> WIRE_INDEX =
            EnumUtils.index(GitHubOrganizationMembersKind.class, GitHubOrganizationMembersKind::wireValue);

    private final String wireValue;

    GitHubOrganizationMembersKind(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Members kind from a wire value.
     *
     * @param value query parameter value
     * @return matching kind
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known kind
     */
    @JsonCreator
    public static GitHubOrganizationMembersKind fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub organization members kind: " + value));
    }
}
