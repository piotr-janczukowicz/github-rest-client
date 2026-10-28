package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sort direction of the accounts list for a marketplace plan. */
@CatalogSchema({
    "operations/apps/list-accounts-for-plan/parameters/2",
    "operations/apps/list-accounts-for-plan-stubbed/parameters/2"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListAccountsForPlanDirection {
    /** Oldest accounts first. */
    ASC("asc"),
    /** Newest accounts first. */
    DESC("desc");

    private static final EnumUtils.WireIndex<GitHubListAccountsForPlanDirection> WIRE_INDEX =
            EnumUtils.index(GitHubListAccountsForPlanDirection.class, GitHubListAccountsForPlanDirection::wireValue);

    private final String wireValue;

    GitHubListAccountsForPlanDirection(final String wireValue) {
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
     * Sort direction from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubListAccountsForPlanDirection fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub list accounts direction: " + value));
    }
}
