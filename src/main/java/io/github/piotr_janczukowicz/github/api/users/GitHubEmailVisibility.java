package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Denotes whether an email is publicly visible.
 *
 * <p>Wire values: {@code public}, {@code private}.
 */
@CatalogSchema("operations/users/set-primary-email-visibility-for-authenticated-user/requestBody/properties/visibility")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubEmailVisibility {
    PUBLIC("public"),
    PRIVATE("private");

    private static final EnumUtils.WireIndex<GitHubEmailVisibility> WIRE_INDEX =
            EnumUtils.index(GitHubEmailVisibility.class, GitHubEmailVisibility::wireValue);

    private final String wireValue;

    GitHubEmailVisibility(final String wireValue) {
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
     * Email visibility from a wire value.
     *
     * @param value JSON value
     * @return matching visibility
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known visibility
     */
    @JsonCreator
    public static GitHubEmailVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub email visibility: " + value));
    }
}
