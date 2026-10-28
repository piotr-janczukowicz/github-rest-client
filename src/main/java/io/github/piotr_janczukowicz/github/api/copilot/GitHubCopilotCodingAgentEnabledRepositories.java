package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Policy for which repositories can use Copilot cloud agent. */
@CatalogSchema({
    "operations/copilot/set-copilot-coding-agent-permissions-organization/requestBody/properties/enabled_repositories",
    "operations/copilot/get-copilot-coding-agent-permissions-organization/200/properties/enabled_repositories"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCopilotCodingAgentEnabledRepositories {
    ALL("all"),
    SELECTED("selected"),
    NONE("none");

    private static final EnumUtils.WireIndex<GitHubCopilotCodingAgentEnabledRepositories> WIRE_INDEX = EnumUtils.index(
            GitHubCopilotCodingAgentEnabledRepositories.class, GitHubCopilotCodingAgentEnabledRepositories::wireValue);

    private final String wireValue;

    GitHubCopilotCodingAgentEnabledRepositories(final String wireValue) {
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
     * Enabled repositories policy from a wire value.
     *
     * @param value JSON value
     * @return matching policy
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known policy
     */
    @JsonCreator
    public static GitHubCopilotCodingAgentEnabledRepositories fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown Copilot coding agent policy: " + value));
    }
}
