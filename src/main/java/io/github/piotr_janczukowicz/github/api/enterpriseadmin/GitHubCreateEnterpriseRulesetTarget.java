package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Target of an enterprise ruleset. */
@CatalogSchema("operations/repos/create-enterprise-ruleset/requestBody/properties/target")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubCreateEnterpriseRulesetTarget {
    /** Branch. */
    BRANCH("branch"),
    /** Tag. */
    TAG("tag"),
    /** Push. */
    PUSH("push"),
    /** Repository. */
    REPOSITORY("repository");

    private static final EnumUtils.WireIndex<GitHubCreateEnterpriseRulesetTarget> WIRE_INDEX =
            EnumUtils.index(GitHubCreateEnterpriseRulesetTarget.class, GitHubCreateEnterpriseRulesetTarget::wireValue);

    private final String wireValue;

    GitHubCreateEnterpriseRulesetTarget(final String wireValue) {
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
     * Value from a wire string.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCreateEnterpriseRulesetTarget fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub create enterprise ruleset target: " + value));
    }
}
