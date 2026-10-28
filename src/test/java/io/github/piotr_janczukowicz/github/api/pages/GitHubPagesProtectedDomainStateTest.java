package io.github.piotr_janczukowicz.github.api.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubPagesProtectedDomainStateTest {

    @ParameterizedTest
    @CsvSource({"pending,PENDING", "verified,VERIFIED", "unverified,UNVERIFIED"})
    void fromWire_shouldReturnType_whenKnownValue(final String wire, final GitHubPagesProtectedDomainState value) {
        final GitHubPagesProtectedDomainState decoded = GitHubPagesProtectedDomainState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubPagesProtectedDomainState.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final GitHubPagesProtectedDomainState decoded = GitHubPagesProtectedDomainState.fromWire("brand_new_value");

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubPagesProtectedDomainState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
