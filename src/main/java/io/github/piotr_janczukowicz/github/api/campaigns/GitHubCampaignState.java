package io.github.piotr_janczukowicz.github.api.campaigns;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Indicates whether a campaign is open or closed. */
@CatalogSchema("components/schemas/campaign-state")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCampaignState {
    /** Campaign is open. */
    OPEN("open"),
    /** Campaign is closed. */
    CLOSED("closed");

    private static final EnumUtils.WireIndex<GitHubCampaignState> WIRE_INDEX =
            EnumUtils.index(GitHubCampaignState.class, GitHubCampaignState::wireValue);

    private final String wireValue;

    GitHubCampaignState(final String wireValue) {
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
     * Resolves campaign state from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCampaignState
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known campaign state
     */
    @JsonCreator
    public static GitHubCampaignState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub campaign state: " + value));
    }
}
