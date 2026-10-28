package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Artifact deployment record status. */
@CatalogSchema("operations/orgs/create-artifact-deployment-record/requestBody/properties/status")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubArtifactDeploymentStatus {
    DEPLOYED("deployed"),
    DECOMMISSIONED("decommissioned");

    private static final EnumUtils.WireIndex<GitHubArtifactDeploymentStatus> WIRE_INDEX =
            EnumUtils.index(GitHubArtifactDeploymentStatus.class, GitHubArtifactDeploymentStatus::wireValue);

    private final String wireValue;

    GitHubArtifactDeploymentStatus(final String wireValue) {
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
     * Deployment status from a wire value.
     *
     * @param value JSON value
     * @return matching status
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known status
     */
    @JsonCreator
    public static GitHubArtifactDeploymentStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub artifact deployment status: " + value));
    }
}
