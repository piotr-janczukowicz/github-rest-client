package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pull request merge method. */
@CatalogSchema({
    "operations/pulls/merge/requestBody/properties/merge_method",
    "operations/pulls/merge-async/requestBody/properties/merge_method"
})
public enum GitHubPullRequestMergeMethod {
    MERGE("merge"),
    SQUASH("squash"),
    REBASE("rebase");

    private static final EnumUtils.WireIndex<GitHubPullRequestMergeMethod> WIRE_INDEX =
            EnumUtils.index(GitHubPullRequestMergeMethod.class, GitHubPullRequestMergeMethod::wireValue);

    private final String wireValue;

    GitHubPullRequestMergeMethod(final String wireValue) {
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
     * Merge method from a wire value.
     *
     * @param value JSON value
     * @return matching method
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known method
     */
    @JsonCreator
    public static GitHubPullRequestMergeMethod fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub pull request merge method: " + value));
    }
}
