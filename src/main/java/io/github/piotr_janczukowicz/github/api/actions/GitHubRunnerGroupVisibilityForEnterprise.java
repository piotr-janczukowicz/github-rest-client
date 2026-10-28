package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Visibility of a runner group of an enterprise. */
@CatalogSchema({
    "operations/enterprise-admin/create-self-hosted-runner-group-for-enterprise/requestBody/properties/visibility",
    "operations/enterprise-admin/update-self-hosted-runner-group-for-enterprise/requestBody/properties/visibility"
})
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubRunnerGroupVisibilityForEnterprise {
    SELECTED("selected"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubRunnerGroupVisibilityForEnterprise> WIRE_INDEX = EnumUtils.index(
            GitHubRunnerGroupVisibilityForEnterprise.class, GitHubRunnerGroupVisibilityForEnterprise::wireValue);

    private final String wireValue;

    GitHubRunnerGroupVisibilityForEnterprise(final String wireValue) {
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
    public static GitHubRunnerGroupVisibilityForEnterprise fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubRunnerGroupVisibilityForEnterprise value: " + value));
    }
}
