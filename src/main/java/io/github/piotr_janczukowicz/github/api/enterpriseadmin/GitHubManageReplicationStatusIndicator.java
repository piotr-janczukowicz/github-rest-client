package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** GitHubManageReplicationStatusIndicator */
@CatalogSchema("components/schemas/ghes-replication-status-indicator")
@Flavors(GitHubFlavor.GHES)
public enum GitHubManageReplicationStatusIndicator {
    UNKNOWN("UNKNOWN"),
    OK("OK"),
    WARNING("WARNING"),
    CRITICAL("CRITICAL");

    private static final EnumUtils.WireIndex<GitHubManageReplicationStatusIndicator> WIRE_INDEX = EnumUtils.index(
            GitHubManageReplicationStatusIndicator.class, GitHubManageReplicationStatusIndicator::wireValue);

    private final String wireValue;

    GitHubManageReplicationStatusIndicator(final String wireValue) {
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
     * ManageReplicationStatusIndicator from a wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not known
     */
    @JsonCreator
    public static GitHubManageReplicationStatusIndicator fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown ManageReplicationStatusIndicator: " + value));
    }
}
