package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Scope of a budget. */
@CatalogSchema({
    "components/schemas/budget/properties/budget_scope",
    "components/schemas/get_all_budgets/properties/budgets/items/properties/budget_scope",
    "components/schemas/create-budget/properties/budget/properties/budget_scope",
    "components/schemas/update-budget/properties/budget/properties/budget_scope"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubBudgetBudgetScope {
    ENTERPRISE("enterprise"),
    ORGANIZATION("organization"),
    REPOSITORY("repository"),
    COST_CENTER("cost_center"),
    MULTI_USER_CUSTOMER("multi_user_customer"),
    MULTI_USER_COST_CENTER("multi_user_cost_center"),
    USER("user"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubBudgetBudgetScope> WIRE_INDEX =
            EnumUtils.index(GitHubBudgetBudgetScope.class, GitHubBudgetBudgetScope::wireValue);

    private final String wireValue;

    GitHubBudgetBudgetScope(final String wireValue) {
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
     * Resolves scope of a budget from its wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubBudgetBudgetScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
