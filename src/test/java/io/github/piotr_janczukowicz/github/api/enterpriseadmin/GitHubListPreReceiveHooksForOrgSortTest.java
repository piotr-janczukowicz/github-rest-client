package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubListPreReceiveHooksForOrgSortTest {

    @ParameterizedTest
    @CsvSource({"created,CREATED", "updated,UPDATED", "name,NAME"})
    void fromWire_shouldReturnConstant_whenKnownValue(
            final String wire, final GitHubListPreReceiveHooksForOrgSort value) {
        final GitHubListPreReceiveHooksForOrgSort decoded = GitHubListPreReceiveHooksForOrgSort.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubListPreReceiveHooksForOrgSort.fromWire("other"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("other");
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubListPreReceiveHooksForOrgSort.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
