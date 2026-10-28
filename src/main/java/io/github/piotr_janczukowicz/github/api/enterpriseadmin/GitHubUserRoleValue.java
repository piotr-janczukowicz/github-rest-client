package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The role value representing a user role in GitHub. */
@CatalogSchema("components/schemas/user-role/items/properties/value")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubUserRoleValue {
    /** User. */
    USER("user"),
    /** 27d9891d-2c17-4f45-a262-781a0e55c80a. */
    ROLE_27D9891D("27d9891d-2c17-4f45-a262-781a0e55c80a"),
    /** Guest collaborator. */
    GUEST_COLLABORATOR("guest_collaborator"),
    /** 1ebc4a02-e56c-43a6-92a5-02ee09b90824. */
    ROLE_1EBC4A02("1ebc4a02-e56c-43a6-92a5-02ee09b90824"),
    /** Enterprise owner. */
    ENTERPRISE_OWNER("enterprise_owner"),
    /** 981df190-8801-4618-a08a-d91f6206c954. */
    ROLE_981DF190("981df190-8801-4618-a08a-d91f6206c954"),
    /** ba4987ab-a1c3-412a-b58c-360fc407cb10. */
    ROLE_BA4987AB("ba4987ab-a1c3-412a-b58c-360fc407cb10"),
    /** Billing manager. */
    BILLING_MANAGER("billing_manager"),
    /** 0e338b8c-cc7f-498a-928d-ea3470d7e7e3. */
    ROLE_0E338B8C("0e338b8c-cc7f-498a-928d-ea3470d7e7e3"),
    /** e6be2762-e4ad-4108-b72d-1bbe884a0f91. */
    ROLE_E6BE2762("e6be2762-e4ad-4108-b72d-1bbe884a0f91");

    private static final EnumUtils.WireIndex<GitHubUserRoleValue> WIRE_INDEX =
            EnumUtils.index(GitHubUserRoleValue.class, GitHubUserRoleValue::wireValue);

    private final String wireValue;

    GitHubUserRoleValue(final String wireValue) {
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
     * User role value from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubUserRoleValue fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub user role value: " + value));
    }
}
