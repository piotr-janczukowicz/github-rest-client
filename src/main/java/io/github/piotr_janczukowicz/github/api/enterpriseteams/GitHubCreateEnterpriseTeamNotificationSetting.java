package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The notification setting the team is set to. */
@CatalogSchema("operations/enterprise-teams/create/requestBody/properties/notification_setting")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubCreateEnterpriseTeamNotificationSetting {
    /** Team members receive notifications when the team is mentioned. */
    NOTIFICATIONS_ENABLED("notifications_enabled"),
    /** No one receives notifications. */
    NOTIFICATIONS_DISABLED("notifications_disabled");

    private static final EnumUtils.WireIndex<GitHubCreateEnterpriseTeamNotificationSetting> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCreateEnterpriseTeamNotificationSetting.class,
                    GitHubCreateEnterpriseTeamNotificationSetting::wireValue);

    private final String wireValue;

    GitHubCreateEnterpriseTeamNotificationSetting(final String wireValue) {
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
     * Notification setting from a wire value.
     *
     * @param value JSON value
     * @return matching setting
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known setting
     */
    @JsonCreator
    public static GitHubCreateEnterpriseTeamNotificationSetting fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub create enterprise team notification setting: " + value));
    }
}
