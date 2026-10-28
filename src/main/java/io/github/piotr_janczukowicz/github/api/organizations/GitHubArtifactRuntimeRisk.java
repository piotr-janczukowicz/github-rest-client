package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Artifact deployment runtime risk classification. */
@CatalogSchema("components/schemas/artifact-deployment-record/properties/runtime_risks/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubArtifactRuntimeRisk {
    CRITICAL_RESOURCE("critical-resource"),
    INTERNET_EXPOSED("internet-exposed"),
    LATERAL_MOVEMENT("lateral-movement"),
    SENSITIVE_DATA("sensitive-data");

    private static final EnumUtils.WireIndex<GitHubArtifactRuntimeRisk> WIRE_INDEX =
            EnumUtils.index(GitHubArtifactRuntimeRisk.class, GitHubArtifactRuntimeRisk::wireValue);

    private final String wireValue;

    GitHubArtifactRuntimeRisk(final String wireValue) {
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
     * Runtime risk from a wire value.
     *
     * @param value JSON value
     * @return matching runtime risk
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known runtime risk
     */
    @JsonCreator
    public static GitHubArtifactRuntimeRisk fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub artifact runtime risk: " + value));
    }
}
