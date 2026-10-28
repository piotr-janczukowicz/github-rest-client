package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Default merge commit message. */
@CatalogSchema("components/schemas/repository/properties/merge_commit_message")
public enum GitHubMergeCommitMessage {
    PR_BODY("PR_BODY"),
    PR_TITLE("PR_TITLE"),
    BLANK("BLANK");

    private static final EnumUtils.WireIndex<GitHubMergeCommitMessage> WIRE_INDEX =
            EnumUtils.index(GitHubMergeCommitMessage.class, GitHubMergeCommitMessage::wireValue);

    private final String wireValue;

    GitHubMergeCommitMessage(final String wireValue) {
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
     * Default merge commit message from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubMergeCommitMessage fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub merge commit message: " + value));
    }
}
