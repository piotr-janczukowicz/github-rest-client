package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Artifact storage record status. */
@CatalogSchema("operations/orgs/create-artifact-storage-record/requestBody/properties/status")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubArtifactStorageStatus {
    ACTIVE("active"),
    EOL("eol"),
    DELETED("deleted");

    private static final EnumUtils.WireIndex<GitHubArtifactStorageStatus> WIRE_INDEX =
            EnumUtils.index(GitHubArtifactStorageStatus.class, GitHubArtifactStorageStatus::wireValue);

    private final String wireValue;

    GitHubArtifactStorageStatus(final String wireValue) {
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
     * Storage status from a wire value.
     *
     * @param value JSON value
     * @return matching status
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known status
     */
    @JsonCreator
    public static GitHubArtifactStorageStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub artifact storage status: " + value));
    }
}
