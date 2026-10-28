package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether a credential has effective access. */
@CatalogSchema("components/schemas/enterprise-token-inventory-item/properties/effective_access_state")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubEnterpriseTokenInventoryItemEffectiveAccessState {
    /** Effective. */
    EFFECTIVE("effective"),
    /** Not effective. */
    NOT_EFFECTIVE("not_effective"),
    /** Unknown. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubEnterpriseTokenInventoryItemEffectiveAccessState> WIRE_INDEX =
            EnumUtils.index(
                    GitHubEnterpriseTokenInventoryItemEffectiveAccessState.class,
                    GitHubEnterpriseTokenInventoryItemEffectiveAccessState::wireValue);

    private final String wireValue;

    GitHubEnterpriseTokenInventoryItemEffectiveAccessState(final String wireValue) {
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
     * Value from a wire string.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubEnterpriseTokenInventoryItemEffectiveAccessState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
