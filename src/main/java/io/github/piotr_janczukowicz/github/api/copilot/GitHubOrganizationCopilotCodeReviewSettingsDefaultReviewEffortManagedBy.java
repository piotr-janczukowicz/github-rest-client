package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether the enterprise or the organization controls the setting. */
@CatalogSchema(
        "components/schemas/organization-copilot-code-review-settings/properties/default_review_effort/properties/managed_by")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffortManagedBy {
    ENTERPRISE("enterprise"),
    ORGANIZATION("organization"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffortManagedBy>
            WIRE_INDEX = EnumUtils.index(
                    GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffortManagedBy.class,
                    GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffortManagedBy::wireValue);

    private final String wireValue;

    GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffortManagedBy(final String wireValue) {
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
    public static GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffortManagedBy fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
