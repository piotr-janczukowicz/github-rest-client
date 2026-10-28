package io.github.piotr_janczukowicz.github.api.agenttasks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** State of the task. */
@CatalogSchema("operations/agent-tasks/get-task-by-repo-and-id/200/properties/state")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubGetTaskByRepoAndIdResponseState {
    /** Queued. */
    QUEUED("queued"),
    /** In progress. */
    IN_PROGRESS("in_progress"),
    /** Completed. */
    COMPLETED("completed"),
    /** Failed. */
    FAILED("failed"),
    /** Idle. */
    IDLE("idle"),
    /** Waiting for user. */
    WAITING_FOR_USER("waiting_for_user"),
    /** Timed out. */
    TIMED_OUT("timed_out"),
    /** Cancelled. */
    CANCELLED("cancelled"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubGetTaskByRepoAndIdResponseState> WIRE_INDEX = EnumUtils.index(
            GitHubGetTaskByRepoAndIdResponseState.class, GitHubGetTaskByRepoAndIdResponseState::wireValue);

    private final String wireValue;

    GitHubGetTaskByRepoAndIdResponseState(final String wireValue) {
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
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubGetTaskByRepoAndIdResponseState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
