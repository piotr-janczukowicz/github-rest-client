package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.math.BigDecimal;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Issue field value wire shape ({@code oneOf} string, number, or string array). */
@CatalogSchema({
    "operations/issues/create/requestBody/properties/issue_field_values/items/properties/value",
    "operations/issues/add-issue-field-values/requestBody/properties/issue_field_values/items/properties/value",
    "operations/issues/update/200/properties/suggestions/properties/issue_field_values/items/properties/value"
})
public record GitHubIssueFieldValueWire(
        @Nullable String textValue,
        @Nullable BigDecimal numberValue,
        @Nullable List<String> listValue) {

    private static final int EXACTLY_ONE_BRANCH = 1;

    public GitHubIssueFieldValueWire {
        final int branches = (textValue != null ? 1 : 0) + (numberValue != null ? 1 : 0) + (listValue != null ? 1 : 0);

        if (branches != EXACTLY_ONE_BRANCH) {
            throw new IllegalArgumentException("exactly one of textValue, numberValue, or listValue is required");
        }

        if (listValue != null) {
            listValue = List.copyOf(listValue);
        }
    }

    /**
     * String field value.
     *
     * @param value value
     * @return text value
     */
    public static GitHubIssueFieldValueWire text(final String value) {
        requireNonNull(value, "value");
        return new GitHubIssueFieldValueWire(value, null, null);
    }

    /**
     * Numeric field value. Any JSON number fits: the value is kept exactly, with no rounding to {@code double}.
     * Equality follows {@link BigDecimal#equals}, so {@code 1.0} and {@code 1.00} differ; compare with
     * {@link BigDecimal#compareTo} to ignore the scale.
     *
     * @param value JSON {@code number} value
     * @return numeric value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static GitHubIssueFieldValueWire number(final BigDecimal value) {
        requireNonNull(value, "value");
        return new GitHubIssueFieldValueWire(null, value, null);
    }

    /**
     * Numeric field value from a {@code long}, sent without a fraction.
     *
     * @param value value
     * @return numeric value
     */
    public static GitHubIssueFieldValueWire number(final long value) {
        return number(BigDecimal.valueOf(value));
    }

    /**
     * Numeric field value from a {@code double}, converted through its shortest decimal form; a whole value is sent
     * without a fraction.
     *
     * @param value finite value
     * @return numeric value
     * @throws IllegalArgumentException if {@code value} is NaN or infinite, which JSON cannot carry
     */
    public static GitHubIssueFieldValueWire number(final double value) {
        return number(IssueNumbers.wireDecimal(value));
    }

    /**
     * Multi-select field value.
     *
     * @param values option names
     * @return list value
     */
    public static GitHubIssueFieldValueWire texts(final List<String> values) {
        requireNonNull(values, "values");
        return new GitHubIssueFieldValueWire(null, null, values);
    }

    /**
     * Wire JSON value for Jackson and docs schema alignment.
     *
     * @return string, number, or string list branch value
     */
    @JsonValue
    public Object jsonValue() {
        if (textValue != null) {
            return textValue;
        }
        if (numberValue != null) {
            return numberValue;
        }
        return requireNonNull(listValue, "listValue");
    }
}
