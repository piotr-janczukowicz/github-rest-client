package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** A GitHub event that can trigger Actions workflows. */
@CatalogSchema(
        "components/schemas/actions-rule-restrict-action-events/properties/parameters/properties/allowed_events/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubActionsRuleRestrictActionEventsAllowedEvent {
    BRANCH_PROTECTION_RULE("branch_protection_rule"),
    CHECK_RUN("check_run"),
    CHECK_SUITE("check_suite"),
    CREATE("create"),
    DELETE("delete"),
    DEPLOYMENT("deployment"),
    DEPLOYMENT_STATUS("deployment_status"),
    DISCUSSION("discussion"),
    DISCUSSION_COMMENT("discussion_comment"),
    FORK("fork"),
    GOLLUM("gollum"),
    IMAGE_VERSION("image_version"),
    ISSUE_COMMENT("issue_comment"),
    ISSUES("issues"),
    LABEL("label"),
    MERGE_GROUP("merge_group"),
    MILESTONE("milestone"),
    PAGE_BUILD("page_build"),
    PROJECT("project"),
    PROJECT_CARD("project_card"),
    PROJECT_COLUMN("project_column"),
    PUBLIC("public"),
    PULL_REQUEST("pull_request"),
    PULL_REQUEST_REVIEW("pull_request_review"),
    PULL_REQUEST_REVIEW_COMMENT("pull_request_review_comment"),
    PULL_REQUEST_TARGET("pull_request_target"),
    PUSH("push"),
    REGISTRY_PACKAGE("registry_package"),
    RELEASE("release"),
    REPOSITORY_DISPATCH("repository_dispatch"),
    SCHEDULE("schedule"),
    STATUS("status"),
    WATCH("watch"),
    WORKFLOW_CALL("workflow_call"),
    WORKFLOW_DISPATCH("workflow_dispatch"),
    WORKFLOW_RUN("workflow_run");

    private static final EnumUtils.WireIndex<GitHubActionsRuleRestrictActionEventsAllowedEvent> WIRE_INDEX =
            EnumUtils.index(
                    GitHubActionsRuleRestrictActionEventsAllowedEvent.class,
                    GitHubActionsRuleRestrictActionEventsAllowedEvent::wireValue);

    private final String wireValue;

    GitHubActionsRuleRestrictActionEventsAllowedEvent(final String wireValue) {
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
     * Allowed event from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubActionsRuleRestrictActionEventsAllowedEvent fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubActionsRuleRestrictActionEventsAllowedEvent value: " + value));
    }
}
