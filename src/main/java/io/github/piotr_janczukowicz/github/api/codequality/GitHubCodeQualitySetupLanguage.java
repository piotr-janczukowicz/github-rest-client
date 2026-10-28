package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Code quality setup language. */
@CatalogSchema("components/schemas/code-quality-setup/properties/languages/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodeQualitySetupLanguage {
    CSHARP("csharp"),
    GO("go"),
    JAVA_KOTLIN("java-kotlin"),
    JAVASCRIPT_TYPESCRIPT("javascript-typescript"),
    PYTHON("python"),
    RUBY("ruby"),
    RUST("rust"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeQualitySetupLanguage> WIRE_INDEX =
            EnumUtils.index(GitHubCodeQualitySetupLanguage.class, GitHubCodeQualitySetupLanguage::wireValue);

    private final String wireValue;

    GitHubCodeQualitySetupLanguage(final String wireValue) {
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
     * Resolves code quality setup language from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeQualitySetupLanguage
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeQualitySetupLanguage fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
