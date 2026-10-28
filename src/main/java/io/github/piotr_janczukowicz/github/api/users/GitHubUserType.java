package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Account type returned on user objects.
 *
 * <p>Wire values: {@code User}, {@code Organization}, {@code Bot}.
 */
@CatalogSchema("components/schemas/public-user/properties/type")
public enum GitHubUserType {
    USER("User"),
    ORGANIZATION("Organization"),
    BOT("Bot"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubUserType> WIRE_INDEX =
            EnumUtils.index(GitHubUserType.class, GitHubUserType::wireValue);

    private final String wireValue;

    GitHubUserType(final String wireValue) {
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
     * Account type from a wire value.
     *
     * @param value JSON value
     * @return matching account type
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubUserType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
