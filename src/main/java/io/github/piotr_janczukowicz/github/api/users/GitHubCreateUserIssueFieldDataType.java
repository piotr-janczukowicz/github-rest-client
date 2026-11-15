package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The data type of the issue field. */
@CatalogSchema("components/schemas/user-create-issue-field/properties/data_type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCreateUserIssueFieldDataType {
    TEXT("text"),
    DATE("date"),
    SINGLE_SELECT("single_select"),
    MULTI_SELECT("multi_select"),
    NUMBER("number");

    private static final EnumUtils.WireIndex<GitHubCreateUserIssueFieldDataType> WIRE_INDEX =
            EnumUtils.index(GitHubCreateUserIssueFieldDataType.class, GitHubCreateUserIssueFieldDataType::wireValue);

    private final String wireValue;

    GitHubCreateUserIssueFieldDataType(final String wireValue) {
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
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCreateUserIssueFieldDataType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub create user issue field data type: " + value));
    }
}
