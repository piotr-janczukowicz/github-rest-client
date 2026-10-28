package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Scope to filter budgets by. */
@CatalogSchema("operations/billing/get-all-budgets-org/parameters/3")
@Flavors(GitHubFlavor.CLOUD)
public enum GitHubGetAllBudgetsOrgScope {
    ENTERPRISE("enterprise"),
    ORGANIZATION("organization"),
    REPOSITORY("repository"),
    COST_CENTER("cost_center"),
    MULTI_USER_CUSTOMER("multi_user_customer"),
    USER("user");

    private static final EnumUtils.WireIndex<GitHubGetAllBudgetsOrgScope> WIRE_INDEX =
            EnumUtils.index(GitHubGetAllBudgetsOrgScope.class, GitHubGetAllBudgetsOrgScope::wireValue);

    private final String wireValue;

    GitHubGetAllBudgetsOrgScope(final String wireValue) {
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
     * Resolves scope to filter budgets by from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubGetAllBudgetsOrgScope
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubGetAllBudgetsOrgScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHubGetAllBudgetsOrgScope value: " + value));
    }
}
