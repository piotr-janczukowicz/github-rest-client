package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The field to sort results by. Can be {@code updated_at} or {@code created_at}. */
@CatalogSchema("operations/agent-automations/list-repo-automation-tasks/parameters/5")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListRepoAutomationTasksSort {
    UPDATED_AT("updated_at"),
    CREATED_AT("created_at");

    private static final EnumUtils.WireIndex<GitHubListRepoAutomationTasksSort> WIRE_INDEX =
            EnumUtils.index(GitHubListRepoAutomationTasksSort.class, GitHubListRepoAutomationTasksSort::wireValue);

    private final String wireValue;

    GitHubListRepoAutomationTasksSort(final String wireValue) {
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
    public static GitHubListRepoAutomationTasksSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub list automation tasks sort: " + value));
    }
}
