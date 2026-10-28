package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Override the Automation {@code write_scope} for runs started by this trigger. If omitted, the automation's
 * {@code write_scope} is used. Organization or repository policy may restrict the effective scope to {@code trigger}.
 */
@CatalogSchema(
        "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_merge_conflict/properties/write_scope")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCreateRepoAutomationTriggersPullRequestMergeConflictWriteScope {
    TRIGGER("trigger"),
    REPOSITORY("repository");

    private static final EnumUtils.WireIndex<GitHubCreateRepoAutomationTriggersPullRequestMergeConflictWriteScope>
            WIRE_INDEX = EnumUtils.index(
                    GitHubCreateRepoAutomationTriggersPullRequestMergeConflictWriteScope.class,
                    GitHubCreateRepoAutomationTriggersPullRequestMergeConflictWriteScope::wireValue);

    private final String wireValue;

    GitHubCreateRepoAutomationTriggersPullRequestMergeConflictWriteScope(final String wireValue) {
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
    public static GitHubCreateRepoAutomationTriggersPullRequestMergeConflictWriteScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub create repo automation triggers pull request merge conflict write scope: "
                                + value));
    }
}
