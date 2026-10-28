package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Scope of an organization budget. */
@CatalogSchema("operations/billing/create-organization-budget/requestBody/properties/budget_scope")
@Flavors(GitHubFlavor.CLOUD)
public enum GitHubCreateOrganizationBudgetBudgetScope {
    ORGANIZATION("organization"),
    REPOSITORY("repository"),
    MULTI_USER_CUSTOMER("multi_user_customer"),
    USER("user");

    private static final EnumUtils.WireIndex<GitHubCreateOrganizationBudgetBudgetScope> WIRE_INDEX = EnumUtils.index(
            GitHubCreateOrganizationBudgetBudgetScope.class, GitHubCreateOrganizationBudgetBudgetScope::wireValue);

    private final String wireValue;

    GitHubCreateOrganizationBudgetBudgetScope(final String wireValue) {
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
     * Resolves scope of an organization budget from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCreateOrganizationBudgetBudgetScope
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCreateOrganizationBudgetBudgetScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubCreateOrganizationBudgetBudgetScope value: " + value));
    }
}
