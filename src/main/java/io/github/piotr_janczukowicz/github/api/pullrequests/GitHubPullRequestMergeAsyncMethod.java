package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pull request asynchronous merge method reported in merge-async result details. */
@CatalogSchema("components/schemas/pull-request-merge-async-result/properties/details/anyOf/0/properties/merge_method")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubPullRequestMergeAsyncMethod {
    DEFAULT("default"),
    MERGE("merge"),
    SQUASH("squash"),
    REBASE("rebase"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubPullRequestMergeAsyncMethod> WIRE_INDEX =
            EnumUtils.index(GitHubPullRequestMergeAsyncMethod.class, GitHubPullRequestMergeAsyncMethod::wireValue);

    private final String wireValue;

    GitHubPullRequestMergeAsyncMethod(final String wireValue) {
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
     * Asynchronous merge method from a wire value.
     *
     * @param value JSON value
     * @return matching method
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubPullRequestMergeAsyncMethod fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
