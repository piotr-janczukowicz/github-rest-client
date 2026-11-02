package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The action of a package rule. */
@CatalogSchema("components/schemas/enterprise-license-policy-item/properties/packages/items/properties/action")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubEnterpriseLicensePolicyItemPackageAction {
    /** unknown. */
    UNKNOWN("unknown"),
    /** allowed. */
    ALLOWED("allowed"),
    /** private. */
    PRIVATE("private"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubEnterpriseLicensePolicyItemPackageAction> WIRE_INDEX =
            EnumUtils.index(
                    GitHubEnterpriseLicensePolicyItemPackageAction.class,
                    GitHubEnterpriseLicensePolicyItemPackageAction::wireValue);

    private final String wireValue;

    GitHubEnterpriseLicensePolicyItemPackageAction(final String wireValue) {
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
     * Resolves the action of a package rule from its wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNRECOGNIZED} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubEnterpriseLicensePolicyItemPackageAction fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
