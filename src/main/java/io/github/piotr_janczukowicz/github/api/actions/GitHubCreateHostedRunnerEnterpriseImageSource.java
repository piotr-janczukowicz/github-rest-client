package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The source of the runner image. */
@CatalogSchema("operations/actions/create-hosted-runner-for-enterprise/requestBody/properties/image/properties/source")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubCreateHostedRunnerEnterpriseImageSource {
    GITHUB("github"),
    PARTNER("partner"),
    CUSTOM("custom");

    private static final EnumUtils.WireIndex<GitHubCreateHostedRunnerEnterpriseImageSource> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCreateHostedRunnerEnterpriseImageSource.class,
                    GitHubCreateHostedRunnerEnterpriseImageSource::wireValue);

    private final String wireValue;

    GitHubCreateHostedRunnerEnterpriseImageSource(final String wireValue) {
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
    public static GitHubCreateHostedRunnerEnterpriseImageSource fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubCreateHostedRunnerEnterpriseImageSource value: " + value));
    }
}
