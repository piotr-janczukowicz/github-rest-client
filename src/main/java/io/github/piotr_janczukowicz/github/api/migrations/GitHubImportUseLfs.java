package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Whether to store large files during the import.
 *
 * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this endpoint
 *     is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
 *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
 */
@Deprecated(since = "2023-10-12")
@CatalogSchema("operations/migrations/set-lfs-preference/requestBody/properties/use_lfs")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubImportUseLfs {
    OPT_IN("opt_in"),
    OPT_OUT("opt_out");

    private static final EnumUtils.WireIndex<GitHubImportUseLfs> WIRE_INDEX =
            EnumUtils.index(GitHubImportUseLfs.class, GitHubImportUseLfs::wireValue);

    private final String wireValue;

    GitHubImportUseLfs(final String wireValue) {
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
     * Import LFS preference from a wire value.
     *
     * @param value JSON value
     * @return matching preference
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known preference
     */
    @JsonCreator
    public static GitHubImportUseLfs fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub import LFS preference: " + value));
    }
}
