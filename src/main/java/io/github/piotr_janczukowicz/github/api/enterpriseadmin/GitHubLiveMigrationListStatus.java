package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Filter migrations by status. */
@CatalogSchema("operations/enterprise-admin/live-migration-list/parameters/2")
@Flavors(GitHubFlavor.GHES)
public enum GitHubLiveMigrationListStatus {
    CREATED("created"),
    QUEUED("queued"),
    IN_PROGRESS("in_progress"),
    PAUSED("paused"),
    COMPLETED("completed"),
    FAILED("failed"),
    TERMINATED("terminated"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubLiveMigrationListStatus> WIRE_INDEX =
            EnumUtils.index(GitHubLiveMigrationListStatus.class, GitHubLiveMigrationListStatus::wireValue);

    private final String wireValue;

    GitHubLiveMigrationListStatus(final String wireValue) {
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
     * status from a wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not known
     */
    @JsonCreator
    public static GitHubLiveMigrationListStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value).orElseThrow(() -> new IllegalArgumentException("Unknown status: " + value));
    }
}
