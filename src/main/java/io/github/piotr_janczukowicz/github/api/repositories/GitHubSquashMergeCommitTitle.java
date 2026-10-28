package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Default squash merge commit title. */
@CatalogSchema("components/schemas/repository/properties/squash_merge_commit_title")
public enum GitHubSquashMergeCommitTitle {
    PR_TITLE("PR_TITLE"),
    COMMIT_OR_PR_TITLE("COMMIT_OR_PR_TITLE");

    private static final EnumUtils.WireIndex<GitHubSquashMergeCommitTitle> WIRE_INDEX =
            EnumUtils.index(GitHubSquashMergeCommitTitle.class, GitHubSquashMergeCommitTitle::wireValue);

    private final String wireValue;

    GitHubSquashMergeCommitTitle(final String wireValue) {
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
     * Default squash merge commit title from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubSquashMergeCommitTitle fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub squash merge commit title: " + value));
    }
}
