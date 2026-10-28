package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Data type of an organization issue field ({@code #/components/schemas/issue-field}). Distinct from
 * {@link io.github.piotr_janczukowicz.github.api.search.GitHubIssueFieldDataType} ({@code issue-field-value}).
 */
@CatalogSchema("components/schemas/issue-field/properties/data_type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubIssueFieldDataType {
    TEXT("text"),
    DATE("date"),
    SINGLE_SELECT("single_select"),
    MULTI_SELECT("multi_select"),
    NUMBER("number");

    private static final EnumUtils.WireIndex<GitHubIssueFieldDataType> WIRE_INDEX =
            EnumUtils.index(GitHubIssueFieldDataType.class, GitHubIssueFieldDataType::wireValue);

    private final String wireValue;

    GitHubIssueFieldDataType(final String wireValue) {
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
     * Issue field data type from a wire value.
     *
     * @param value JSON value
     * @return matching data type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known data type
     */
    @JsonCreator
    public static GitHubIssueFieldDataType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub issue field data type: " + value));
    }
}
