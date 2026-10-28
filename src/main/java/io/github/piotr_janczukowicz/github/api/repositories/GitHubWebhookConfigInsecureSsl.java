package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.math.BigDecimal;
import org.jspecify.annotations.Nullable;

/**
 * Webhook SSL verification flag ({@code oneOf} string or number).
 *
 * @param textValue String value ({@code "0"} or {@code "1"})
 * @param numberValue Numeric value, read exactly as a {@link BigDecimal}
 */
@CatalogSchema("components/schemas/webhook-config-insecure-ssl")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubWebhookConfigInsecureSsl(
        @Nullable String textValue, @Nullable BigDecimal numberValue) {

    private static final String PARAM_VALUE = "value";

    public GitHubWebhookConfigInsecureSsl {
        final boolean hasText = textValue != null;
        final boolean hasNumber = numberValue != null;

        if (hasText == hasNumber) {
            throw new IllegalArgumentException("exactly one of textValue or numberValue is required");
        }
    }

    /**
     * SSL verification flag from a string value.
     *
     * @param value SSL verification flag as text, for example {@code "0"} or {@code "1"}
     * @return text value
     */
    public static GitHubWebhookConfigInsecureSsl text(final String value) {
        requireNonNull(value, PARAM_VALUE);
        return new GitHubWebhookConfigInsecureSsl(value, null);
    }

    /**
     * SSL verification flag from a numeric value. Equality follows {@link BigDecimal#equals}, so {@code 1} and
     * {@code 1.0} differ.
     *
     * @param value SSL verification flag as a number, {@code 0} or {@code 1}
     * @return numeric value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static GitHubWebhookConfigInsecureSsl number(final BigDecimal value) {
        requireNonNull(value, PARAM_VALUE);
        return new GitHubWebhookConfigInsecureSsl(null, value);
    }

    /**
     * SSL verification flag from an integer value.
     *
     * @param value SSL verification flag as a number, {@code 0} or {@code 1}
     * @return numeric value
     */
    public static GitHubWebhookConfigInsecureSsl number(final long value) {
        return number(BigDecimal.valueOf(value));
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return string or numeric wire value
     */
    @JsonValue
    public Object jsonValue() {
        final String text = textValue;
        if (text != null) {
            return text;
        }
        return requireNonNull(numberValue, "numberValue");
    }

    /**
     * SSL verification flag from a JSON number.
     *
     * @param value JSON number
     * @return numeric value
     */
    @JsonCreator
    public static GitHubWebhookConfigInsecureSsl fromJson(final BigDecimal value) {
        return number(requireNonNull(value, PARAM_VALUE));
    }

    /**
     * SSL verification flag from a JSON string.
     *
     * @param value JSON string
     * @return text value
     */
    @JsonCreator
    public static GitHubWebhookConfigInsecureSsl fromJson(final String value) {
        return text(value);
    }
}
