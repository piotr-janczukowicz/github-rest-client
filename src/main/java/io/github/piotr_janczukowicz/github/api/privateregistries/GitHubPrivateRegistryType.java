package io.github.piotr_janczukowicz.github.api.privateregistries;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Private registry type. */
@CatalogSchema({
    "components/schemas/org-private-registry-configuration/properties/registry_type",
    "components/schemas/org-private-registry-configuration-with-selected-repositories/properties/registry_type",
    "operations/private-registries/create-org-private-registry/requestBody/properties/registry_type",
    "operations/private-registries/update-org-private-registry/requestBody/properties/registry_type"
})
public enum GitHubPrivateRegistryType {
    MAVEN_REPOSITORY("maven_repository"),
    NUGET_FEED("nuget_feed"),
    GOPROXY_SERVER("goproxy_server"),
    NPM_REGISTRY("npm_registry"),
    RUBYGEMS_SERVER("rubygems_server"),
    CARGO_REGISTRY("cargo_registry"),
    COMPOSER_REPOSITORY("composer_repository"),
    DOCKER_REGISTRY("docker_registry"),
    GIT_SOURCE("git_source"),
    HELM_REGISTRY("helm_registry"),
    HEX_ORGANIZATION("hex_organization"),
    HEX_REPOSITORY("hex_repository"),
    PUB_REPOSITORY("pub_repository"),
    PYTHON_INDEX("python_index"),
    TERRAFORM_REGISTRY("terraform_registry");

    private static final EnumUtils.WireIndex<GitHubPrivateRegistryType> WIRE_INDEX =
            EnumUtils.index(GitHubPrivateRegistryType.class, GitHubPrivateRegistryType::wireValue);

    private final String wireValue;

    GitHubPrivateRegistryType(final String wireValue) {
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
     * Registry type from a wire value.
     *
     * @param value JSON value
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubPrivateRegistryType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub private registry type: " + value));
    }
}
