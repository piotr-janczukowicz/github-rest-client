package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The package's language or package management ecosystem. */
@CatalogSchema("components/schemas/security-advisory-ecosystems")
public enum GitHubSecurityAdvisoryEcosystem {
    RUBYGEMS("rubygems"),
    NPM("npm"),
    PIP("pip"),
    MAVEN("maven"),
    NUGET("nuget"),
    COMPOSER("composer"),
    GO("go"),
    RUST("rust"),
    ERLANG("erlang"),
    ACTIONS("actions"),
    PUB("pub"),
    OTHER("other"),
    SWIFT("swift");

    private static final EnumUtils.WireIndex<GitHubSecurityAdvisoryEcosystem> WIRE_INDEX =
            EnumUtils.index(GitHubSecurityAdvisoryEcosystem.class, GitHubSecurityAdvisoryEcosystem::wireValue);

    private final String wireValue;

    GitHubSecurityAdvisoryEcosystem(final String wireValue) {
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
     * Resolves security advisory ecosystem from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecurityAdvisoryEcosystem
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known security advisory ecosystem
     */
    @JsonCreator
    public static GitHubSecurityAdvisoryEcosystem fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub security advisory ecosystem: " + value));
    }
}
