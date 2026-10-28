package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Controls which repository resources a run may modify. {@code trigger} limits writes to the resource that started the
 * run, when one exists, and resources created during the run; {@code repository} allows writes to any resource in the
 * repository. Supported values: {@code trigger}, {@code repository}. Omit to leave unchanged.
 */
@CatalogSchema("operations/agent-automations/edit-repo-automation/requestBody/properties/write_scope")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubEditRepoAutomationWriteScope {
    TRIGGER("trigger"),
    REPOSITORY("repository");

    private static final EnumUtils.WireIndex<GitHubEditRepoAutomationWriteScope> WIRE_INDEX =
            EnumUtils.index(GitHubEditRepoAutomationWriteScope.class, GitHubEditRepoAutomationWriteScope::wireValue);

    private final String wireValue;

    GitHubEditRepoAutomationWriteScope(final String wireValue) {
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
    public static GitHubEditRepoAutomationWriteScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub edit repo automation write scope: " + value));
    }
}
