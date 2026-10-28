package io.github.piotr_janczukowicz.github.api.issues;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Number conversion shared by the issue wire value types. */
final class IssueNumbers {

    private IssueNumbers() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * The shortest decimal form of {@code value}; a whole value has no fraction digits, so {@code 42.0} is sent as
     * {@code 42}, the form GitHub expects for a whole number.
     *
     * @param value finite value
     * @return the decimal
     * @throws IllegalArgumentException if {@code value} is NaN or infinite, which JSON cannot carry
     */
    static BigDecimal wireDecimal(final double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("value must be finite: " + value);
        }
        final BigDecimal decimal = BigDecimal.valueOf(value);
        return decimal.scale() > 0 && decimal.stripTrailingZeros().scale() <= 0
                ? decimal.setScale(0, RoundingMode.UNNECESSARY)
                : decimal;
    }
}
