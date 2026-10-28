package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The property to sort the results by. */
@CatalogSchema("operations/enterprise-admin/list-pre-receive-hooks/parameters/3")
@Flavors(GitHubFlavor.GHES)
public enum GitHubListPreReceiveHooksSort {
    CREATED("created"),
    UPDATED("updated"),
    NAME("name");

    private static final EnumUtils.WireIndex<GitHubListPreReceiveHooksSort> WIRE_INDEX =
            EnumUtils.index(GitHubListPreReceiveHooksSort.class, GitHubListPreReceiveHooksSort::wireValue);

    private final String wireValue;

    GitHubListPreReceiveHooksSort(final String wireValue) {
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
     * sort from a wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not known
     */
    @JsonCreator
    public static GitHubListPreReceiveHooksSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value).orElseThrow(() -> new IllegalArgumentException("Unknown sort: " + value));
    }
}
