package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Wire values of GitHubDiscussionStateReason. */
@CatalogSchema("components/schemas/discussion/properties/state_reason")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubDiscussionStateReason {
    /** resolved. */
    RESOLVED("resolved"),
    /** outdated. */
    OUTDATED("outdated"),
    /** duplicate. */
    DUPLICATE("duplicate"),
    /** reopened. */
    REOPENED("reopened"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubDiscussionStateReason> WIRE_INDEX =
            EnumUtils.index(GitHubDiscussionStateReason.class, GitHubDiscussionStateReason::wireValue);

    private final String wireValue;

    GitHubDiscussionStateReason(final String wireValue) {
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
    public static GitHubDiscussionStateReason fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
