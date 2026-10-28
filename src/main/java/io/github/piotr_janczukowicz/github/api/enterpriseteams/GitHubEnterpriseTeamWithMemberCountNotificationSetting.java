package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether team members receive notifications when the team is mentioned. */
@CatalogSchema("components/schemas/enterprise-team-with-member-count/properties/notification_setting")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubEnterpriseTeamWithMemberCountNotificationSetting {
    /** Team members receive notifications when the team is mentioned. */
    NOTIFICATIONS_ENABLED("notifications_enabled"),
    /** No one receives notifications. */
    NOTIFICATIONS_DISABLED("notifications_disabled"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubEnterpriseTeamWithMemberCountNotificationSetting> WIRE_INDEX =
            EnumUtils.index(
                    GitHubEnterpriseTeamWithMemberCountNotificationSetting.class,
                    GitHubEnterpriseTeamWithMemberCountNotificationSetting::wireValue);

    private final String wireValue;

    GitHubEnterpriseTeamWithMemberCountNotificationSetting(final String wireValue) {
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
     */
    @JsonCreator
    public static GitHubEnterpriseTeamWithMemberCountNotificationSetting fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
