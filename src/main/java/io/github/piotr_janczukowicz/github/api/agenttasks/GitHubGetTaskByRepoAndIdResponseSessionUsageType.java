package io.github.piotr_janczukowicz.github.api.agenttasks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Billing unit used for the session. */
@CatalogSchema(
        "operations/agent-tasks/get-task-by-repo-and-id/200/properties/sessions/items/properties/usage/properties/type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubGetTaskByRepoAndIdResponseSessionUsageType {
    /** Ai credits. */
    AI_CREDITS("ai_credits"),
    /** Premium requests. */
    PREMIUM_REQUESTS("premium_requests"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubGetTaskByRepoAndIdResponseSessionUsageType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubGetTaskByRepoAndIdResponseSessionUsageType.class,
                    GitHubGetTaskByRepoAndIdResponseSessionUsageType::wireValue);

    private final String wireValue;

    GitHubGetTaskByRepoAndIdResponseSessionUsageType(final String wireValue) {
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
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubGetTaskByRepoAndIdResponseSessionUsageType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
