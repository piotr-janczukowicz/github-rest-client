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
        "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_review_submitted/properties/states/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState {
    APPROVED("approved"),
    CHANGES_REQUESTED("changes_requested"),
    COMMENTED("commented");

    private static final EnumUtils.WireIndex<GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState>
            WIRE_INDEX = EnumUtils.index(
                    GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState.class,
                    GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState::wireValue);

    private final String wireValue;

    GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState(final String wireValue) {
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
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub create repo automation triggers pull request review submitted state: "
                                + value));
    }
}
