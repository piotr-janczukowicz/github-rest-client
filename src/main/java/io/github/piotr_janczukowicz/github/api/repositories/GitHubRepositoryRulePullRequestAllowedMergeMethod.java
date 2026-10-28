package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Allowed merge method for a pull request repository rule. */
@CatalogSchema(
        "components/schemas/repository-rule-pull-request/properties/parameters/properties/allowed_merge_methods/items")
public enum GitHubRepositoryRulePullRequestAllowedMergeMethod {
    MERGE("merge"),
    SQUASH("squash"),
    REBASE("rebase");

    private static final EnumUtils.WireIndex<GitHubRepositoryRulePullRequestAllowedMergeMethod> WIRE_INDEX =
            EnumUtils.index(
                    GitHubRepositoryRulePullRequestAllowedMergeMethod.class,
                    GitHubRepositoryRulePullRequestAllowedMergeMethod::wireValue);

    private final String wireValue;

    GitHubRepositoryRulePullRequestAllowedMergeMethod(final String wireValue) {
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
     * Allowed merge method for a pull request repository rule from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRulePullRequestAllowedMergeMethod fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository rule pull request allowed merge method: " + value));
    }
}
