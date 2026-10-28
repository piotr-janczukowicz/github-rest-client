package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * What to do with alerts associated with the deleted patterns. `delete_alerts` permanently removes the alerts.
 * `resolve_alerts` resolves the alerts as "pattern deleted". Defaults to `delete_alerts` when not specified.
 */
@CatalogSchema({
    "operations/secret-scanning/bulk-delete-org-custom-patterns/requestBody/properties/post_delete_action",
    "operations/secret-scanning/bulk-delete-repo-custom-patterns/requestBody/properties/post_delete_action"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubSecretScanningCustomPatternPostDeleteAction {
    DELETE_ALERTS("delete_alerts"),
    RESOLVE_ALERTS("resolve_alerts");

    private static final EnumUtils.WireIndex<GitHubSecretScanningCustomPatternPostDeleteAction> WIRE_INDEX =
            EnumUtils.index(
                    GitHubSecretScanningCustomPatternPostDeleteAction.class,
                    GitHubSecretScanningCustomPatternPostDeleteAction::wireValue);

    private final String wireValue;

    GitHubSecretScanningCustomPatternPostDeleteAction(final String wireValue) {
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
     * Resolves secret scanning custom pattern post delete action from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningCustomPatternPostDeleteAction
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning custom pattern post delete
     *     action
     */
    @JsonCreator
    public static GitHubSecretScanningCustomPatternPostDeleteAction fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub secret scanning custom pattern post delete action: " + value));
    }
}
