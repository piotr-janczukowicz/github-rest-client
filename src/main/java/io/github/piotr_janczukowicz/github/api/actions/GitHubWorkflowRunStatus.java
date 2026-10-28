package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Workflow run status query filter. */
@CatalogSchema("components/parameters/workflow-run-status/schema")
public enum GitHubWorkflowRunStatus {
    COMPLETED("completed"),
    ACTION_REQUIRED("action_required"),
    CANCELLED("cancelled"),
    FAILURE("failure"),
    NEUTRAL("neutral"),
    SKIPPED("skipped"),
    STALE("stale"),
    SUCCESS("success"),
    TIMED_OUT("timed_out"),
    IN_PROGRESS("in_progress"),
    QUEUED("queued"),
    REQUESTED("requested"),
    WAITING("waiting"),
    PENDING("pending");

    private static final EnumUtils.WireIndex<GitHubWorkflowRunStatus> WIRE_INDEX =
            EnumUtils.index(GitHubWorkflowRunStatus.class, GitHubWorkflowRunStatus::wireValue);

    private final String wireValue;

    GitHubWorkflowRunStatus(final String wireValue) {
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
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubWorkflowRunStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHubWorkflowRunStatus value: " + value));
    }
}
