package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Wire values of GitHubForkEventForkeePullRequestCreationPolicy. */
@CatalogSchema("components/schemas/fork-event/properties/forkee/properties/pull_request_creation_policy")
public enum GitHubForkEventForkeePullRequestCreationPolicy {
    /** all. */
    ALL("all"),
    /** collaborators_only. */
    COLLABORATORS_ONLY("collaborators_only"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubForkEventForkeePullRequestCreationPolicy> WIRE_INDEX =
            EnumUtils.index(
                    GitHubForkEventForkeePullRequestCreationPolicy.class,
                    GitHubForkEventForkeePullRequestCreationPolicy::wireValue);

    private final String wireValue;

    GitHubForkEventForkeePullRequestCreationPolicy(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * JSON {@code wireValue} of this constant.
     *
     * @return wire value
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Parses the wire value.
     *
     * @param value wire value
     * @return constant
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubForkEventForkeePullRequestCreationPolicy fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
