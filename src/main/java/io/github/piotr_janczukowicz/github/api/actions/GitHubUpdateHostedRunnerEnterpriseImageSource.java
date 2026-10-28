package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The source type of the runner image to use. */
@CatalogSchema("operations/actions/update-hosted-runner-for-enterprise/requestBody/properties/image_source")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubUpdateHostedRunnerEnterpriseImageSource {
    GITHUB("github"),
    PARTNER("partner"),
    CUSTOM("custom");

    private static final EnumUtils.WireIndex<GitHubUpdateHostedRunnerEnterpriseImageSource> WIRE_INDEX =
            EnumUtils.index(
                    GitHubUpdateHostedRunnerEnterpriseImageSource.class,
                    GitHubUpdateHostedRunnerEnterpriseImageSource::wireValue);

    private final String wireValue;

    GitHubUpdateHostedRunnerEnterpriseImageSource(final String wireValue) {
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
    public static GitHubUpdateHostedRunnerEnterpriseImageSource fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubUpdateHostedRunnerEnterpriseImageSource value: " + value));
    }
}
