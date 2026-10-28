package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The notification setting the team has set. */
@CatalogSchema({
    "components/schemas/team-full/properties/notification_setting",
    "operations/teams/create/requestBody/properties/notification_setting",
    "operations/teams/update-in-org/requestBody/properties/notification_setting",
    "operations/teams/update-legacy/requestBody/properties/notification_setting"
})
public enum GitHubTeamNotificationSetting {
    NOTIFICATIONS_ENABLED("notifications_enabled"),
    NOTIFICATIONS_DISABLED("notifications_disabled");

    private static final EnumUtils.WireIndex<GitHubTeamNotificationSetting> WIRE_INDEX =
            EnumUtils.index(GitHubTeamNotificationSetting.class, GitHubTeamNotificationSetting::wireValue);

    private final String wireValue;

    GitHubTeamNotificationSetting(final String wireValue) {
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
    public static GitHubTeamNotificationSetting fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub team notification setting: " + value));
    }
}
