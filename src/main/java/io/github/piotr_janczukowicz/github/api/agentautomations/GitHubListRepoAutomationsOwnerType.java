package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Filter automations by owner type. {@code user} returns automations owned by their creator, {@code repository} returns
 * automations owned by the repository. Omit to return both.
 */
@CatalogSchema("operations/agent-automations/list-repo-automations/parameters/4")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListRepoAutomationsOwnerType {
    USER("user"),
    REPOSITORY("repository");

    private static final EnumUtils.WireIndex<GitHubListRepoAutomationsOwnerType> WIRE_INDEX =
            EnumUtils.index(GitHubListRepoAutomationsOwnerType.class, GitHubListRepoAutomationsOwnerType::wireValue);

    private final String wireValue;

    GitHubListRepoAutomationsOwnerType(final String wireValue) {
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
    public static GitHubListRepoAutomationsOwnerType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub list repo automations owner type: " + value));
    }
}
