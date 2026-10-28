package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of resource to create. */
@CatalogSchema({
    "operations/copilot-spaces/create-resource-for-org/requestBody/properties/resource_type",
    "operations/copilot-spaces/create-resource-for-user/requestBody/properties/resource_type"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCreateResourceType {
    REPOSITORY("repository"),
    GITHUB_FILE("github_file"),
    FREE_TEXT("free_text"),
    GITHUB_ISSUE("github_issue"),
    GITHUB_PULL_REQUEST("github_pull_request");

    private static final EnumUtils.WireIndex<GitHubCreateResourceType> WIRE_INDEX =
            EnumUtils.index(GitHubCreateResourceType.class, GitHubCreateResourceType::wireValue);

    private final String wireValue;

    GitHubCreateResourceType(final String wireValue) {
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
     * Resolves create Copilot Space resource type from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCreateResourceType
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known create Copilot Space resource type
     */
    @JsonCreator
    public static GitHubCreateResourceType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub create Copilot Space resource type: " + value));
    }
}
