package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pending organization invitation source filter. */
@CatalogSchema("operations/orgs/list-pending-invitations/parameters/4")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubOrganizationPendingInvitationSource {
    ALL("all"),
    MEMBER("member"),
    SCIM("scim");

    private static final EnumUtils.WireIndex<GitHubOrganizationPendingInvitationSource> WIRE_INDEX = EnumUtils.index(
            GitHubOrganizationPendingInvitationSource.class, GitHubOrganizationPendingInvitationSource::wireValue);

    private final String wireValue;

    GitHubOrganizationPendingInvitationSource(final String wireValue) {
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
     * Invitation source filter from a wire value.
     *
     * @param value query parameter value
     * @return matching source filter
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known source filter
     */
    @JsonCreator
    public static GitHubOrganizationPendingInvitationSource fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub organization pending invitation source filter: " + value));
    }
}
