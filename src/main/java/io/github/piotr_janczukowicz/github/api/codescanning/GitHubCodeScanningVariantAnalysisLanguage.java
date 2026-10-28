package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The language targeted by the CodeQL query */
@CatalogSchema("components/schemas/code-scanning-variant-analysis-language")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodeScanningVariantAnalysisLanguage {
    ACTIONS("actions"),
    CPP("cpp"),
    CSHARP("csharp"),
    GO("go"),
    JAVA("java"),
    JAVASCRIPT("javascript"),
    PYTHON("python"),
    RUBY("ruby"),
    RUST("rust"),
    SWIFT("swift");

    private static final EnumUtils.WireIndex<GitHubCodeScanningVariantAnalysisLanguage> WIRE_INDEX = EnumUtils.index(
            GitHubCodeScanningVariantAnalysisLanguage.class, GitHubCodeScanningVariantAnalysisLanguage::wireValue);

    private final String wireValue;

    GitHubCodeScanningVariantAnalysisLanguage(final String wireValue) {
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
     * Resolves code scanning variant analysis language from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningVariantAnalysisLanguage
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code scanning variant analysis language
     */
    @JsonCreator
    public static GitHubCodeScanningVariantAnalysisLanguage fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub code scanning variant analysis language: " + value));
    }
}
