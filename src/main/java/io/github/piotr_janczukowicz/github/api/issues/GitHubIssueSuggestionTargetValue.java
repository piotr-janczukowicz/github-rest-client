package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.math.BigDecimal;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Issue suggestion target value wire shape ({@code oneOf} string, number, boolean, or string array). */
@CatalogSchema("components/schemas/issue-suggestion/properties/target_value")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubIssueSuggestionTargetValue(
        @Nullable String textValue,
        @Nullable BigDecimal numberValue,
        @Nullable Boolean booleanValue,
        @Nullable List<String> listValue) {

    private static final int EXACTLY_ONE_BRANCH = 1;

    public GitHubIssueSuggestionTargetValue {
        final int branches = (textValue != null ? 1 : 0)
                + (numberValue != null ? 1 : 0)
                + (booleanValue != null ? 1 : 0)
                + (listValue != null ? 1 : 0);

        if (branches != EXACTLY_ONE_BRANCH) {
            throw new IllegalArgumentException(
                    "exactly one of textValue, numberValue, booleanValue, or listValue is required");
        }
    }

    /**
     * String target value.
     *
     * @param value value
     * @return text value
     */
    public static GitHubIssueSuggestionTargetValue text(final String value) {
        requireNonNull(value, "value");
        return new GitHubIssueSuggestionTargetValue(value, null, null, null);
    }

    /**
     * Numeric target value. Any JSON number fits: the value is kept exactly, with no rounding to {@code double}.
     * Equality follows {@link BigDecimal#equals}, so {@code 1.0} and {@code 1.00} differ; compare with
     * {@link BigDecimal#compareTo} to ignore the scale.
     *
     * @param value JSON {@code number} value
     * @return numeric value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static GitHubIssueSuggestionTargetValue number(final BigDecimal value) {
        requireNonNull(value, "value");
        return new GitHubIssueSuggestionTargetValue(null, value, null, null);
    }

    /**
     * Numeric target value from a {@code long}, sent without a fraction.
     *
     * @param value value
     * @return numeric value
     */
    public static GitHubIssueSuggestionTargetValue number(final long value) {
        return number(BigDecimal.valueOf(value));
    }

    /**
     * Numeric target value from a {@code double}, converted through its shortest decimal form; a whole value is sent
     * without a fraction.
     *
     * @param value finite value
     * @return numeric value
     * @throws IllegalArgumentException if {@code value} is NaN or infinite, which JSON cannot carry
     */
    public static GitHubIssueSuggestionTargetValue number(final double value) {
        return number(IssueNumbers.wireDecimal(value));
    }

    /**
     * Boolean target value.
     *
     * @param value value
     * @return boolean value
     */
    public static GitHubIssueSuggestionTargetValue flag(final boolean value) {
        return new GitHubIssueSuggestionTargetValue(null, null, value, null);
    }

    /**
     * Multi-select target value.
     *
     * @param values option names
     * @return list value
     */
    public static GitHubIssueSuggestionTargetValue texts(final List<String> values) {
        requireNonNull(values, "values");
        return new GitHubIssueSuggestionTargetValue(null, null, null, values);
    }

    /**
     * Wire JSON value for Jackson and docs schema alignment.
     *
     * @return string, number, boolean, or string list branch value
     */
    @JsonValue
    public Object jsonValue() {
        if (textValue != null) {
            return textValue;
        }
        if (numberValue != null) {
            return numberValue;
        }
        if (booleanValue != null) {
            return booleanValue;
        }
        return requireNonNull(listValue, "listValue");
    }
}
