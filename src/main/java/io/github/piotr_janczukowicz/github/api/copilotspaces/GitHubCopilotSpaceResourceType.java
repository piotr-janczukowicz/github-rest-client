package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of the resource. */
@CatalogSchema({
    "components/schemas/copilot-space-resource/properties/resource_type",
    "components/schemas/copilot-space/properties/resources_attributes/items/properties/resource_type",
    "operations/copilot-spaces/create-for-org/requestBody/properties/resources_attributes/items/properties/resource_type",
    "operations/copilot-spaces/update-for-org/requestBody/properties/resources_attributes/items/properties/resource_type",
    "operations/copilot-spaces/create-for-user/requestBody/properties/resources_attributes/items/properties/resource_type",
    "operations/copilot-spaces/update-for-user/requestBody/properties/resources_attributes/items/properties/resource_type"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCopilotSpaceResourceType {
    REPOSITORY("repository"),
    GITHUB_FILE("github_file"),
    FREE_TEXT("free_text"),
    GITHUB_ISSUE("github_issue"),
    GITHUB_PULL_REQUEST("github_pull_request"),
    MEDIA_CONTENT("media_content"),
    UPLOADED_TEXT_FILE("uploaded_text_file");

    private static final EnumUtils.WireIndex<GitHubCopilotSpaceResourceType> WIRE_INDEX =
            EnumUtils.index(GitHubCopilotSpaceResourceType.class, GitHubCopilotSpaceResourceType::wireValue);

    private final String wireValue;

    GitHubCopilotSpaceResourceType(final String wireValue) {
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
     * Resolves Copilot Space resource type from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCopilotSpaceResourceType
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Copilot Space resource type
     */
    @JsonCreator
    public static GitHubCopilotSpaceResourceType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub Copilot Space resource type: " + value));
    }
}
