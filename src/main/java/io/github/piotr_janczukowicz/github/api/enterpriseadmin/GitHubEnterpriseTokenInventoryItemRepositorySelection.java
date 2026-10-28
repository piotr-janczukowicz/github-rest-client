package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Repository selection for a credential. */
@CatalogSchema("components/schemas/enterprise-token-inventory-item/properties/repository_selection")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubEnterpriseTokenInventoryItemRepositorySelection {
    /** All. */
    ALL("all"),
    /** Subset. */
    SUBSET("subset"),
    /** None. */
    NONE("none");

    private static final EnumUtils.WireIndex<GitHubEnterpriseTokenInventoryItemRepositorySelection> WIRE_INDEX =
            EnumUtils.index(
                    GitHubEnterpriseTokenInventoryItemRepositorySelection.class,
                    GitHubEnterpriseTokenInventoryItemRepositorySelection::wireValue);

    private final String wireValue;

    GitHubEnterpriseTokenInventoryItemRepositorySelection(final String wireValue) {
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
    public static GitHubEnterpriseTokenInventoryItemRepositorySelection fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub enterprise token inventory item repository selection: " + value));
    }
}
