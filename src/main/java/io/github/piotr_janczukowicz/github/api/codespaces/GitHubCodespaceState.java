package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** State of a codespace. */
@CatalogSchema({
    "components/schemas/codespace/properties/state",
    "components/schemas/codespace-with-full-repository/properties/state"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodespaceState {
    UNKNOWN("Unknown"),
    CREATED("Created"),
    QUEUED("Queued"),
    PROVISIONING("Provisioning"),
    AVAILABLE("Available"),
    AWAITING("Awaiting"),
    UNAVAILABLE("Unavailable"),
    DELETED("Deleted"),
    MOVED("Moved"),
    SHUTDOWN("Shutdown"),
    ARCHIVED("Archived"),
    STARTING("Starting"),
    SHUTTING_DOWN("ShuttingDown"),
    FAILED("Failed"),
    EXPORTING("Exporting"),
    UPDATING("Updating"),
    REBUILDING("Rebuilding");

    private static final EnumUtils.WireIndex<GitHubCodespaceState> WIRE_INDEX =
            EnumUtils.index(GitHubCodespaceState.class, GitHubCodespaceState::wireValue);

    private final String wireValue;

    GitHubCodespaceState(final String wireValue) {
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
     * Resolves codespace state from its wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodespaceState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
