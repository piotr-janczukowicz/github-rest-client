package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The type of runner. {@code standard} uses GitHub-hosted runners. {@code labeled} uses runners that match the labels
 * or runner group.
 */
@CatalogSchema("operations/copilot/set-organization-code-review-runner/requestBody/properties/runner_type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubSetOrganizationCodeReviewRunnerRunnerType {
    STANDARD("standard"),
    LABELED("labeled");

    private static final EnumUtils.WireIndex<GitHubSetOrganizationCodeReviewRunnerRunnerType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubSetOrganizationCodeReviewRunnerRunnerType.class,
                    GitHubSetOrganizationCodeReviewRunnerRunnerType::wireValue);

    private final String wireValue;

    GitHubSetOrganizationCodeReviewRunnerRunnerType(final String wireValue) {
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
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubSetOrganizationCodeReviewRunnerRunnerType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub set organization code review runner runner type: " + value));
    }
}
