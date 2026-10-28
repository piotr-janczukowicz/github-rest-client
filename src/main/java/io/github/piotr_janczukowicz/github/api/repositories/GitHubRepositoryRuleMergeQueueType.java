package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** merge_queue type. */
@CatalogSchema("components/schemas/repository-rule-merge-queue/properties/type")
public enum GitHubRepositoryRuleMergeQueueType {
    MERGE_QUEUE("merge_queue");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleMergeQueueType> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryRuleMergeQueueType.class, GitHubRepositoryRuleMergeQueueType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleMergeQueueType(final String wireValue) {
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
     * merge_queue type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleMergeQueueType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub repository rule merge queue type: " + value));
    }
}
