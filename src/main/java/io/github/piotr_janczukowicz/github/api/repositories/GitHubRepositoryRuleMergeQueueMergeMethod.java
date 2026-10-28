package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Merge queue merge method. */
@CatalogSchema("components/schemas/repository-rule-merge-queue/properties/parameters/properties/merge_method")
public enum GitHubRepositoryRuleMergeQueueMergeMethod {
    MERGE("MERGE"),
    SQUASH("SQUASH"),
    REBASE("REBASE");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleMergeQueueMergeMethod> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryRuleMergeQueueMergeMethod.class, GitHubRepositoryRuleMergeQueueMergeMethod::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleMergeQueueMergeMethod(final String wireValue) {
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
     * Merge queue merge method from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleMergeQueueMergeMethod fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository rule merge queue merge method: " + value));
    }
}
