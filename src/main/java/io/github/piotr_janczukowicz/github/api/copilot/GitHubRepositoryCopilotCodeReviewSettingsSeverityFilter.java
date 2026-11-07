package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Which review comment severities Copilot code review leaves out. {@code no_filter} keeps every comment, {@code low}
 * leaves out low-severity comments, and {@code low_and_medium} leaves out low- and medium-severity comments.
 */
@CatalogSchema("components/schemas/repository-copilot-code-review-settings/properties/severity_filter")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubRepositoryCopilotCodeReviewSettingsSeverityFilter {
    NO_FILTER("no_filter"),
    LOW("low"),
    LOW_AND_MEDIUM("low_and_medium"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubRepositoryCopilotCodeReviewSettingsSeverityFilter> WIRE_INDEX =
            EnumUtils.index(
                    GitHubRepositoryCopilotCodeReviewSettingsSeverityFilter.class,
                    GitHubRepositoryCopilotCodeReviewSettingsSeverityFilter::wireValue);

    private final String wireValue;

    GitHubRepositoryCopilotCodeReviewSettingsSeverityFilter(final String wireValue) {
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
     * @return matching constant, or {@link #UNRECOGNIZED} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubRepositoryCopilotCodeReviewSettingsSeverityFilter fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
