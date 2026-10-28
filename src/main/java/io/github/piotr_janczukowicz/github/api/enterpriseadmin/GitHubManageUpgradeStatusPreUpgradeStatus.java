package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GhesVersions;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pre-upgrade phase status. One of: `not started`, `in progress`, `completed`, `failed`. */
@CatalogSchema("components/schemas/ghes-get-manage-upgrade-status-response/items/properties/pre_upgrade_status")
@Flavors(GitHubFlavor.GHES)
@GhesVersions(since = "3.22")
public enum GitHubManageUpgradeStatusPreUpgradeStatus {
    NOT_STARTED("not_started"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed"),
    FAILED("failed"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubManageUpgradeStatusPreUpgradeStatus> WIRE_INDEX = EnumUtils.index(
            GitHubManageUpgradeStatusPreUpgradeStatus.class, GitHubManageUpgradeStatusPreUpgradeStatus::wireValue);

    private final String wireValue;

    GitHubManageUpgradeStatusPreUpgradeStatus(final String wireValue) {
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
     * pre upgrade status from a wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubManageUpgradeStatusPreUpgradeStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
