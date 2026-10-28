package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Scope to filter budgets by. */
@CatalogSchema("operations/billing/get-all-budgets/parameters/3")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubGetAllBudgetsScope {
    ENTERPRISE("enterprise"),
    ORGANIZATION("organization"),
    REPOSITORY("repository"),
    COST_CENTER("cost_center"),
    MULTI_USER_CUSTOMER("multi_user_customer"),
    MULTI_USER_COST_CENTER("multi_user_cost_center"),
    USER("user");

    private static final EnumUtils.WireIndex<GitHubGetAllBudgetsScope> WIRE_INDEX =
            EnumUtils.index(GitHubGetAllBudgetsScope.class, GitHubGetAllBudgetsScope::wireValue);

    private final String wireValue;

    GitHubGetAllBudgetsScope(final String wireValue) {
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
     * Resolves the constant of this enum from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubGetAllBudgetsScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHubGetAllBudgetsScope value: " + value));
    }
}
