package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pull request asynchronous merge action. */
@CatalogSchema({
    "operations/pulls/merge-async/requestBody/properties/merge_action",
    "components/schemas/pull-request-merge-async-result/properties/details/anyOf/0/properties/merge_action"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubPullRequestMergeAsyncAction {
    DEFAULT("default"),
    DIRECT_MERGE("direct_merge"),
    MERGE_QUEUE("merge_queue");

    private static final EnumUtils.WireIndex<GitHubPullRequestMergeAsyncAction> WIRE_INDEX =
            EnumUtils.index(GitHubPullRequestMergeAsyncAction.class, GitHubPullRequestMergeAsyncAction::wireValue);

    private final String wireValue;

    GitHubPullRequestMergeAsyncAction(final String wireValue) {
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
     * Asynchronous merge action from a wire value.
     *
     * @param value JSON value
     * @return matching action
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known action
     */
    @JsonCreator
    public static GitHubPullRequestMergeAsyncAction fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub pull request merge async action: " + value));
    }
}
