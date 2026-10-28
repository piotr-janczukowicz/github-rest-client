package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Price model of a marketplace listing plan. */
@CatalogSchema("components/schemas/marketplace-listing-plan/properties/price_model")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubMarketplaceListingPlanPriceModel {
    /** Free plan. */
    FREE("FREE"),
    /** Flat rate plan. */
    FLAT_RATE("FLAT_RATE"),
    /** Per unit plan. */
    PER_UNIT("PER_UNIT"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubMarketplaceListingPlanPriceModel> WIRE_INDEX = EnumUtils.index(
            GitHubMarketplaceListingPlanPriceModel.class, GitHubMarketplaceListingPlanPriceModel::wireValue);

    private final String wireValue;

    GitHubMarketplaceListingPlanPriceModel(final String wireValue) {
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
     * Price model from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubMarketplaceListingPlanPriceModel fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
