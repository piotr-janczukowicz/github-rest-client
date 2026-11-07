package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of resource. */
@CatalogSchema("components/schemas/billing-cost-center-membership/properties/resource/properties/type")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubBillingCostCenterMembershipResourceType {
    USER("user"),
    ORGANIZATION("organization"),
    REPOSITORY("repository"),
    TEAM("team"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubBillingCostCenterMembershipResourceType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubBillingCostCenterMembershipResourceType.class,
                    GitHubBillingCostCenterMembershipResourceType::wireValue);

    private final String wireValue;

    GitHubBillingCostCenterMembershipResourceType(final String wireValue) {
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
     * @return matching constant, or {@link #UNRECOGNIZED} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubBillingCostCenterMembershipResourceType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
