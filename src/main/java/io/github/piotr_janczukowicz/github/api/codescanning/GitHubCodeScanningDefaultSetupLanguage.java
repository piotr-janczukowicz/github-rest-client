package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Code scanning default setup language. */
@CatalogSchema("components/schemas/code-scanning-default-setup/properties/languages/items")
public enum GitHubCodeScanningDefaultSetupLanguage {
    ACTIONS("actions"),
    C_CPP("c-cpp"),
    CSHARP("csharp"),
    GO("go"),
    JAVA_KOTLIN("java-kotlin"),
    JAVASCRIPT_TYPESCRIPT("javascript-typescript"),
    PYTHON("python"),
    RUBY("ruby"),
    SWIFT("swift"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeScanningDefaultSetupLanguage> WIRE_INDEX = EnumUtils.index(
            GitHubCodeScanningDefaultSetupLanguage.class, GitHubCodeScanningDefaultSetupLanguage::wireValue);

    private final String wireValue;

    GitHubCodeScanningDefaultSetupLanguage(final String wireValue) {
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
     * Resolves code scanning default setup language from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningDefaultSetupLanguage
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeScanningDefaultSetupLanguage fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
