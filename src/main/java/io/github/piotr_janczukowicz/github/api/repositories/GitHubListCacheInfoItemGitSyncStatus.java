package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Synchronization status of the Git data of a cache replica. */
@CatalogSchema("operations/repos/list-cache-info/200/items/properties/git/properties/sync_status")
@Flavors(GitHubFlavor.GHES)
public enum GitHubListCacheInfoItemGitSyncStatus {
    OFFLINE("offline"),
    INACTIVE("inactive"),
    IN_SYNC("in_sync"),
    NOT_IN_SYNC("not_in_sync"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubListCacheInfoItemGitSyncStatus> WIRE_INDEX = EnumUtils.index(
            GitHubListCacheInfoItemGitSyncStatus.class, GitHubListCacheInfoItemGitSyncStatus::wireValue);

    private final String wireValue;

    GitHubListCacheInfoItemGitSyncStatus(final String wireValue) {
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
     * Status from a wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubListCacheInfoItemGitSyncStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
