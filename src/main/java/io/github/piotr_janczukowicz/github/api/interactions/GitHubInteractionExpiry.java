package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The duration of the interaction restriction. */
@CatalogSchema("components/schemas/interaction-expiry")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubInteractionExpiry {
    ONE_DAY("one_day"),
    THREE_DAYS("three_days"),
    ONE_WEEK("one_week"),
    ONE_MONTH("one_month"),
    SIX_MONTHS("six_months");

    private static final EnumUtils.WireIndex<GitHubInteractionExpiry> WIRE_INDEX =
            EnumUtils.index(GitHubInteractionExpiry.class, GitHubInteractionExpiry::wireValue);

    private final String wireValue;

    GitHubInteractionExpiry(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON and query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Interaction expiry for GitHub's {@code expiry} string.
     *
     * @param value JSON or query value
     * @return matching expiry
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known expiry
     */
    @JsonCreator
    public static GitHubInteractionExpiry fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub interaction expiry: " + value));
    }
}
