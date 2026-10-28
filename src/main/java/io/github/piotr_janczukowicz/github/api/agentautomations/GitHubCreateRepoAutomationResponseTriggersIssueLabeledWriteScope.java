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
        "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/issue_labeled/properties/write_scope")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCreateRepoAutomationResponseTriggersIssueLabeledWriteScope {
    TRIGGER("trigger"),
    REPOSITORY("repository");

    private static final EnumUtils.WireIndex<GitHubCreateRepoAutomationResponseTriggersIssueLabeledWriteScope>
            WIRE_INDEX = EnumUtils.index(
                    GitHubCreateRepoAutomationResponseTriggersIssueLabeledWriteScope.class,
                    GitHubCreateRepoAutomationResponseTriggersIssueLabeledWriteScope::wireValue);

    private final String wireValue;

    GitHubCreateRepoAutomationResponseTriggersIssueLabeledWriteScope(final String wireValue) {
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
    public static GitHubCreateRepoAutomationResponseTriggersIssueLabeledWriteScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub create repo automation response triggers issue labeled write scope: " + value));
    }
}
