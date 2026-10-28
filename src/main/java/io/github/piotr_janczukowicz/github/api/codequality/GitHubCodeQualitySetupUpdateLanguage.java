package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Code quality setup language. */
@CatalogSchema("components/schemas/code-quality-setup-update/properties/languages/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodeQualitySetupUpdateLanguage {
    CSHARP("csharp"),
    GO("go"),
    JAVA_KOTLIN("java-kotlin"),
    JAVASCRIPT_TYPESCRIPT("javascript-typescript"),
    PYTHON("python"),
    RUBY("ruby");

    private static final EnumUtils.WireIndex<GitHubCodeQualitySetupUpdateLanguage> WIRE_INDEX = EnumUtils.index(
            GitHubCodeQualitySetupUpdateLanguage.class, GitHubCodeQualitySetupUpdateLanguage::wireValue);

    private final String wireValue;

    GitHubCodeQualitySetupUpdateLanguage(final String wireValue) {
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
     * Resolves code quality setup update language from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeQualitySetupUpdateLanguage
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code quality setup update language
     */
    @JsonCreator
    public static GitHubCodeQualitySetupUpdateLanguage fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub code quality setup update language: " + value));
    }
}
