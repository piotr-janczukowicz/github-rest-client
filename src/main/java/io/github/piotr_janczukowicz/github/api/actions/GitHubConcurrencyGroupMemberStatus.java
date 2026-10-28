package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The status of a concurrency group member. */
@CatalogSchema({
    "components/schemas/concurrency-group/properties/group_members/items/properties/status",
    "components/schemas/concurrency-group-run-list/properties/concurrency_groups/items/properties/group_members/items/properties/status"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubConcurrencyGroupMemberStatus {
    IN_PROGRESS("in_progress"),
    PENDING("pending"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubConcurrencyGroupMemberStatus> WIRE_INDEX =
            EnumUtils.index(GitHubConcurrencyGroupMemberStatus.class, GitHubConcurrencyGroupMemberStatus::wireValue);

    private final String wireValue;

    GitHubConcurrencyGroupMemberStatus(final String wireValue) {
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
     * Type from a wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubConcurrencyGroupMemberStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
