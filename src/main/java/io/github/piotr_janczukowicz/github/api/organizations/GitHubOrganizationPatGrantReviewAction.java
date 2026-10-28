package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Review action for organization programmatic access grant requests. */
@CatalogSchema("operations/orgs/review-pat-grant-requests-in-bulk/requestBody/properties/action")
public enum GitHubOrganizationPatGrantReviewAction {
    APPROVE("approve"),
    DENY("deny");

    private static final EnumUtils.WireIndex<GitHubOrganizationPatGrantReviewAction> WIRE_INDEX = EnumUtils.index(
            GitHubOrganizationPatGrantReviewAction.class, GitHubOrganizationPatGrantReviewAction::wireValue);

    private final String wireValue;

    GitHubOrganizationPatGrantReviewAction(final String wireValue) {
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
     * Review action from a wire value.
     *
     * @param value JSON value
     * @return matching review action
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known review action
     */
    @JsonCreator
    public static GitHubOrganizationPatGrantReviewAction fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub organization PAT grant review action: " + value));
    }
}
