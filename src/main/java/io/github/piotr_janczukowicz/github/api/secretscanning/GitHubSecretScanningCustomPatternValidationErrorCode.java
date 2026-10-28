package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** A machine-readable code describing the error. */
@CatalogSchema("components/schemas/secret-scanning-custom-pattern-validation-error/properties/code")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubSecretScanningCustomPatternValidationErrorCode {
    INVALID("invalid"),
    UNPROCESSABLE("unprocessable"),
    START_DELIMITER("start_delimiter"),
    END_DELIMITER("end_delimiter"),
    NAME("name"),
    MUST_MATCH("must_match"),
    MUST_NOT_MATCH("must_not_match"),
    CUSTOM_PATTERN_VERSION_MISMATCH("custom_pattern_version_mismatch"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubSecretScanningCustomPatternValidationErrorCode> WIRE_INDEX =
            EnumUtils.index(
                    GitHubSecretScanningCustomPatternValidationErrorCode.class,
                    GitHubSecretScanningCustomPatternValidationErrorCode::wireValue);

    private final String wireValue;

    GitHubSecretScanningCustomPatternValidationErrorCode(final String wireValue) {
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
     * Resolves secret scanning custom pattern validation error code from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningCustomPatternValidationErrorCode
     * @throws NullPointerException if {@code value} is {@code null} code
     */
    @JsonCreator
    public static GitHubSecretScanningCustomPatternValidationErrorCode fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
