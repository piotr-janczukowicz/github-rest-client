package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Repository activity type. */
@CatalogSchema("components/schemas/activity/properties/activity_type")
public enum GitHubRepositoryActivityType {
    PUSH("push"),
    FORCE_PUSH("force_push"),
    BRANCH_CREATION("branch_creation"),
    BRANCH_DELETION("branch_deletion"),
    PR_MERGE("pr_merge"),
    MERGE_QUEUE_MERGE("merge_queue_merge");

    private static final EnumUtils.WireIndex<GitHubRepositoryActivityType> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryActivityType.class, GitHubRepositoryActivityType::wireValue);

    private final String wireValue;

    GitHubRepositoryActivityType(final String wireValue) {
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
     * Repository activity type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryActivityType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub repository activity type: " + value));
    }
}
