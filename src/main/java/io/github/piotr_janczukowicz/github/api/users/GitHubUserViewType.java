package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Discriminator for public vs private user JSON ({@code user_view_type}).
 *
 * <p>Wire values: {@code public}, {@code private}.
 */
@CatalogSchema({
    "components/schemas/public-user/properties/user_view_type",
    "components/schemas/private-user/properties/user_view_type",
    "components/schemas/simple-user/properties/user_view_type"
})
public enum GitHubUserViewType {
    PUBLIC("public"),
    PRIVATE("private"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubUserViewType> WIRE_INDEX =
            EnumUtils.index(GitHubUserViewType.class, GitHubUserViewType::wireValue);

    private final String wireValue;

    GitHubUserViewType(final String wireValue) {
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
     * User view type from a wire value.
     *
     * @param value JSON value
     * @return matching view type
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubUserViewType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
