package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Data type of a project field. */
@CatalogSchema("components/schemas/projects-v2-field/properties/data_type")
public enum GitHubProjectFieldDataType {
    ASSIGNEES("assignees"),
    LINKED_PULL_REQUESTS("linked_pull_requests"),
    REVIEWERS("reviewers"),
    LABELS("labels"),
    MILESTONE("milestone"),
    REPOSITORY("repository"),
    TITLE("title"),
    TEXT("text"),
    SINGLE_SELECT("single_select"),
    NUMBER("number"),
    DATE("date"),
    ITERATION("iteration"),
    ISSUE_TYPE("issue_type"),
    PARENT_ISSUE("parent_issue"),
    SUB_ISSUES_PROGRESS("sub_issues_progress");

    private static final EnumUtils.WireIndex<GitHubProjectFieldDataType> WIRE_INDEX =
            EnumUtils.index(GitHubProjectFieldDataType.class, GitHubProjectFieldDataType::wireValue);

    private final String wireValue;

    GitHubProjectFieldDataType(final String wireValue) {
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
     * Field data type from a wire value.
     *
     * @param value JSON value
     * @return matching data type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known data type
     */
    @JsonCreator
    public static GitHubProjectFieldDataType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub project field data type: " + value));
    }
}
