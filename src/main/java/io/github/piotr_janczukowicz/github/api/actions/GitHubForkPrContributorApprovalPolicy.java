package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The policy that controls when fork PR workflows require approval from a maintainer. */
@CatalogSchema("components/schemas/actions-fork-pr-contributor-approval/properties/approval_policy")
public enum GitHubForkPrContributorApprovalPolicy {
    FIRST_TIME_CONTRIBUTORS_NEW_TO_GITHUB("first_time_contributors_new_to_github"),
    FIRST_TIME_CONTRIBUTORS("first_time_contributors"),
    ALL_EXTERNAL_CONTRIBUTORS("all_external_contributors");

    private static final EnumUtils.WireIndex<GitHubForkPrContributorApprovalPolicy> WIRE_INDEX = EnumUtils.index(
            GitHubForkPrContributorApprovalPolicy.class, GitHubForkPrContributorApprovalPolicy::wireValue);

    private final String wireValue;

    GitHubForkPrContributorApprovalPolicy(final String wireValue) {
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
     * Type from a wire value.
     *
     * @param value JSON value
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubForkPrContributorApprovalPolicy fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHubForkPrContributorApprovalPolicy value: " + value));
    }
}
