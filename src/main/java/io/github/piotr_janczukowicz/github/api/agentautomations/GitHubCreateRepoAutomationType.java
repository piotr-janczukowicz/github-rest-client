package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The type of automation, fixed at creation. {@code personal} keeps it private to its creator and its runs act as that
 * user. {@code shared} makes it belong to the repository, visible to anyone with read access to the repository and
 * maintainable by anyone with write access to the repository, and its runs act as the Copilot app rather than as a
 * person. {@code shared} is rejected when repository ownership is not available for the repository. Supported values:
 * {@code personal}, {@code shared}. Defaults to {@code personal}.
 */
@CatalogSchema("operations/agent-automations/create-repo-automation/requestBody/properties/type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCreateRepoAutomationType {
    PERSONAL("personal"),
    SHARED("shared");

    private static final EnumUtils.WireIndex<GitHubCreateRepoAutomationType> WIRE_INDEX =
            EnumUtils.index(GitHubCreateRepoAutomationType.class, GitHubCreateRepoAutomationType::wireValue);

    private final String wireValue;

    GitHubCreateRepoAutomationType(final String wireValue) {
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
    public static GitHubCreateRepoAutomationType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub create repo automation type: " + value));
    }
}
