package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The visibility of the issue field. Can be {@code restricted} (visible only to the owner and to collaborators on the
 * repository the field is read through) or {@code all} (visible to all users who can see issues). Defaults to
 * {@code restricted}.
 */
@CatalogSchema("components/schemas/user-create-issue-field/properties/visibility")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCreateUserIssueFieldVisibility {
    RESTRICTED("restricted"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubCreateUserIssueFieldVisibility> WIRE_INDEX = EnumUtils.index(
            GitHubCreateUserIssueFieldVisibility.class, GitHubCreateUserIssueFieldVisibility::wireValue);

    private final String wireValue;

    GitHubCreateUserIssueFieldVisibility(final String wireValue) {
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
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCreateUserIssueFieldVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub create user issue field visibility: " + value));
    }
}
