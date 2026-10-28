package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sort field for organization personal access token list queries. */
@CatalogSchema("components/parameters/personal-access-token-sort/schema")
public enum GitHubOrganizationPersonalAccessTokenSort {
    CREATED_AT("created_at");

    private static final EnumUtils.WireIndex<GitHubOrganizationPersonalAccessTokenSort> WIRE_INDEX = EnumUtils.index(
            GitHubOrganizationPersonalAccessTokenSort.class, GitHubOrganizationPersonalAccessTokenSort::wireValue);

    private final String wireValue;

    GitHubOrganizationPersonalAccessTokenSort(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Sort field from a wire value.
     *
     * @param value query parameter value
     * @return matching sort field
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known sort field
     */
    @JsonCreator
    public static GitHubOrganizationPersonalAccessTokenSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub organization personal access token sort: " + value));
    }
}
