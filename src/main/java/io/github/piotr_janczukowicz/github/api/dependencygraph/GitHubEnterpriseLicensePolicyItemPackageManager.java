package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The package manager of a package rule. */
@CatalogSchema("components/schemas/enterprise-license-policy-item/properties/packages/items/properties/package_manager")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubEnterpriseLicensePolicyItemPackageManager {
    /** unknown. */
    UNKNOWN("unknown"),
    /** rubygems. */
    RUBYGEMS("rubygems"),
    /** npm. */
    NPM("npm"),
    /** pip. */
    PIP("pip"),
    /** maven. */
    MAVEN("maven"),
    /** nuget. */
    NUGET("nuget"),
    /** composer. */
    COMPOSER("composer"),
    /** gomod. */
    GOMOD("gomod"),
    /** rust. */
    RUST("rust"),
    /** actions. */
    ACTIONS("actions"),
    /** pub. */
    PUB("pub"),
    /** swift. */
    SWIFT("swift");

    private static final EnumUtils.WireIndex<GitHubEnterpriseLicensePolicyItemPackageManager> WIRE_INDEX =
            EnumUtils.index(
                    GitHubEnterpriseLicensePolicyItemPackageManager.class,
                    GitHubEnterpriseLicensePolicyItemPackageManager::wireValue);

    private final String wireValue;

    GitHubEnterpriseLicensePolicyItemPackageManager(final String wireValue) {
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
     * Resolves the package manager of a package rule from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubEnterpriseLicensePolicyItemPackageManager fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub enterprise license policy item package manager: " + value));
    }
}
