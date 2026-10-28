package io.github.piotr_janczukowicz.github.api.agenttasks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The direction to sort the results by. */
@CatalogSchema("operations/agent-tasks/list-tasks-for-repo/parameters/5")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListTasksForRepoDirection {
    /** Asc. */
    ASC("asc"),
    /** Desc. */
    DESC("desc");

    private static final EnumUtils.WireIndex<GitHubListTasksForRepoDirection> WIRE_INDEX =
            EnumUtils.index(GitHubListTasksForRepoDirection.class, GitHubListTasksForRepoDirection::wireValue);

    private final String wireValue;

    GitHubListTasksForRepoDirection(final String wireValue) {
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
    public static GitHubListTasksForRepoDirection fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub list tasks for repo direction: " + value));
    }
}
