package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The data type of the issue field. */
@CatalogSchema("components/schemas/user-issue-field/properties/data_type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubUserIssueFieldDataType {
    TEXT("text"),
    DATE("date"),
    SINGLE_SELECT("single_select"),
    MULTI_SELECT("multi_select"),
    NUMBER("number"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubUserIssueFieldDataType> WIRE_INDEX =
            EnumUtils.index(GitHubUserIssueFieldDataType.class, GitHubUserIssueFieldDataType::wireValue);

    private final String wireValue;

    GitHubUserIssueFieldDataType(final String wireValue) {
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
     * @return matching constant, or {@link #UNRECOGNIZED} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubUserIssueFieldDataType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
