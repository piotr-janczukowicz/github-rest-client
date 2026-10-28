package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The image provider. */
@CatalogSchema({
    "components/schemas/actions-hosted-runner-curated-image/properties/source",
    "operations/actions/create-hosted-runner-for-org/requestBody/properties/image/properties/source",
    "operations/actions/update-hosted-runner-for-org/requestBody/properties/image_source",
    "components/schemas/actions-hosted-runner-pool-image/properties/source"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubHostedRunnerImageSource {
    GITHUB("github"),
    PARTNER("partner"),
    CUSTOM("custom");

    private static final EnumUtils.WireIndex<GitHubHostedRunnerImageSource> WIRE_INDEX =
            EnumUtils.index(GitHubHostedRunnerImageSource.class, GitHubHostedRunnerImageSource::wireValue);

    private final String wireValue;

    GitHubHostedRunnerImageSource(final String wireValue) {
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
     * Type from a wire value.
     *
     * @param value JSON value
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubHostedRunnerImageSource fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHubHostedRunnerImageSource value: " + value));
    }
}
