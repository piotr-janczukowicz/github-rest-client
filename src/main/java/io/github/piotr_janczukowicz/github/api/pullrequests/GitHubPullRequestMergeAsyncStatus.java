package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pull request asynchronous merge status. */
@CatalogSchema("components/schemas/pull-request-merge-async-result/properties/status")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubPullRequestMergeAsyncStatus {
    PENDING("pending"),
    MERGED("merged"),
    ENQUEUED("enqueued"),
    FAILED("failed"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubPullRequestMergeAsyncStatus> WIRE_INDEX =
            EnumUtils.index(GitHubPullRequestMergeAsyncStatus.class, GitHubPullRequestMergeAsyncStatus::wireValue);

    private final String wireValue;

    GitHubPullRequestMergeAsyncStatus(final String wireValue) {
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
     * Asynchronous merge status from a wire value.
     *
     * @param value JSON value
     * @return matching status
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubPullRequestMergeAsyncStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
