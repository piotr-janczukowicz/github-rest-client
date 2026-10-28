package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Target repository visibility. Defaults to `internal` when omitted. A source repository with public visibility must
 * set this explicitly.
 */
@CatalogSchema("operations/enterprise-admin/live-migration-create/requestBody/properties/target_visibility")
@Flavors(GitHubFlavor.GHES)
public enum GitHubLiveMigrationCreatePayloadTargetVisibility {
    PRIVATE("private"),
    INTERNAL("internal");

    private static final EnumUtils.WireIndex<GitHubLiveMigrationCreatePayloadTargetVisibility> WIRE_INDEX =
            EnumUtils.index(
                    GitHubLiveMigrationCreatePayloadTargetVisibility.class,
                    GitHubLiveMigrationCreatePayloadTargetVisibility::wireValue);

    private final String wireValue;

    GitHubLiveMigrationCreatePayloadTargetVisibility(final String wireValue) {
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
     * target visibility from a wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not known
     */
    @JsonCreator
    public static GitHubLiveMigrationCreatePayloadTargetVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown target visibility: " + value));
    }
}
