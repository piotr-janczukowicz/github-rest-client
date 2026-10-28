package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Visibility of an organization issue field. */
@CatalogSchema("components/schemas/issue-field/properties/visibility")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubIssueFieldVisibility {
    ORGANIZATION_MEMBERS_ONLY("organization_members_only"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubIssueFieldVisibility> WIRE_INDEX =
            EnumUtils.index(GitHubIssueFieldVisibility.class, GitHubIssueFieldVisibility::wireValue);

    private final String wireValue;

    GitHubIssueFieldVisibility(final String wireValue) {
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
     * Issue field visibility from a wire value.
     *
     * @param value JSON value
     * @return matching visibility
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known visibility
     */
    @JsonCreator
    public static GitHubIssueFieldVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub issue field visibility: " + value));
    }
}
