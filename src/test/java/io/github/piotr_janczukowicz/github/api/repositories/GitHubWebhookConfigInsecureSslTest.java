package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubWebhookConfigInsecureSslTest {

    @Test
    void constructor_shouldThrow_whenBothBranchesSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubWebhookConfigInsecureSsl("0", BigDecimal.ZERO));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue or numberValue is required");
    }

    @Test
    void constructor_shouldThrow_whenNeitherBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubWebhookConfigInsecureSsl(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue or numberValue is required");
    }

    @Test
    void text_shouldReturnTextValue_whenCalled() {
        final GitHubWebhookConfigInsecureSsl value = GitHubWebhookConfigInsecureSsl.text("0");

        assertSoftly(softly -> {
            softly.assertThat(value.textValue()).isEqualTo("0");
            softly.assertThat(value.numberValue()).isNull();
        });
    }

    @Test
    void number_shouldReturnNumberValue_whenCalled() {
        final GitHubWebhookConfigInsecureSsl value = GitHubWebhookConfigInsecureSsl.number(0);

        assertSoftly(softly -> {
            softly.assertThat(value.textValue()).isNull();
            softly.assertThat(value.numberValue()).isEqualTo(BigDecimal.ZERO);
        });
    }

    @Test
    void number_shouldKeepEveryDigit_whenBigDecimalExceedsDoublePrecision() {
        final BigDecimal huge = new BigDecimal("12345678901234567890.123456789");

        assertThat(GitHubWebhookConfigInsecureSsl.number(huge).numberValue()).isEqualTo(huge);
    }

    @Test
    void jsonValue_shouldReturnText_whenTextBranchSet() {
        final Object jsonValue = GitHubWebhookConfigInsecureSsl.text("1").jsonValue();

        assertThat(jsonValue).isEqualTo("1");
    }

    @Test
    void jsonValue_shouldReturnNumber_whenNumberBranchSet() {
        final Object jsonValue = GitHubWebhookConfigInsecureSsl.number(0).jsonValue();

        assertThat(jsonValue).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void fromJson_shouldReturnTextValue_whenStringProvided() {
        final GitHubWebhookConfigInsecureSsl value = GitHubWebhookConfigInsecureSsl.fromJson("0");

        assertThat(value.textValue()).isEqualTo("0");
    }

    @Test
    void fromJson_shouldReturnNumberValue_whenNumberProvided() {
        final GitHubWebhookConfigInsecureSsl value = GitHubWebhookConfigInsecureSsl.fromJson(BigDecimal.ONE);

        assertThat(value.numberValue()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void text_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubWebhookConfigInsecureSsl.text(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void number_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubWebhookConfigInsecureSsl.number(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
