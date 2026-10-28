package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Submitted review states to match. */
@CatalogSchema(
        "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_review_submitted/properties/states/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmittedState {
    APPROVED("approved"),
    CHANGES_REQUESTED("changes_requested"),
    COMMENTED("commented"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmittedState>
            WIRE_INDEX = EnumUtils.index(
                    GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmittedState.class,
                    GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmittedState::wireValue);

    private final String wireValue;

    GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmittedState(final String wireValue) {
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
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmittedState fromWire(
            final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
