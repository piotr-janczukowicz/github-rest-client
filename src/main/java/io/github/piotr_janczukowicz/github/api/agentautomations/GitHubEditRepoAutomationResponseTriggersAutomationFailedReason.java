package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** reasons */
@CatalogSchema(
        "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/automation_failed/properties/reasons/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubEditRepoAutomationResponseTriggersAutomationFailedReason {
    REPORTED("reported"),
    CRASHED("crashed"),
    TIMED_OUT("timed_out"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubEditRepoAutomationResponseTriggersAutomationFailedReason>
            WIRE_INDEX = EnumUtils.index(
                    GitHubEditRepoAutomationResponseTriggersAutomationFailedReason.class,
                    GitHubEditRepoAutomationResponseTriggersAutomationFailedReason::wireValue);

    private final String wireValue;

    GitHubEditRepoAutomationResponseTriggersAutomationFailedReason(final String wireValue) {
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
    public static GitHubEditRepoAutomationResponseTriggersAutomationFailedReason fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
