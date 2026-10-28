package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Code scanning default setup update language. */
@CatalogSchema("components/schemas/code-scanning-default-setup-update/properties/languages/items")
public enum GitHubCodeScanningDefaultSetupUpdateLanguage {
    ACTIONS("actions"),
    C_CPP("c-cpp"),
    CSHARP("csharp"),
    GO("go"),
    JAVA_KOTLIN("java-kotlin"),
    JAVASCRIPT_TYPESCRIPT("javascript-typescript"),
    PYTHON("python"),
    RUBY("ruby"),
    SWIFT("swift");

    private static final EnumUtils.WireIndex<GitHubCodeScanningDefaultSetupUpdateLanguage> WIRE_INDEX = EnumUtils.index(
            GitHubCodeScanningDefaultSetupUpdateLanguage.class,
            GitHubCodeScanningDefaultSetupUpdateLanguage::wireValue);

    private final String wireValue;

    GitHubCodeScanningDefaultSetupUpdateLanguage(final String wireValue) {
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
     * Resolves code scanning default setup update language from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningDefaultSetupUpdateLanguage
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code scanning default setup update language
     */
    @JsonCreator
    public static GitHubCodeScanningDefaultSetupUpdateLanguage fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub code scanning default setup update language: " + value));
    }
}
