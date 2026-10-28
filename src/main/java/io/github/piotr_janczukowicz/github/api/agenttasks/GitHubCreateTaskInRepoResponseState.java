package io.github.piotr_janczukowicz.github.api.agenttasks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** State of the task. */
@CatalogSchema("operations/agent-tasks/create-task-in-repo/201/properties/state")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCreateTaskInRepoResponseState {
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

    private static final EnumUtils.WireIndex<GitHubCreateTaskInRepoResponseState> WIRE_INDEX =
            EnumUtils.index(GitHubCreateTaskInRepoResponseState.class, GitHubCreateTaskInRepoResponseState::wireValue);

    private final String wireValue;

    GitHubCreateTaskInRepoResponseState(final String wireValue) {
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
    public static GitHubCreateTaskInRepoResponseState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
