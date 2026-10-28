package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubManageVersionVersionPlatformTest {

    @ParameterizedTest
    @CsvSource({"ami,AMI", "azure,AZURE", "esx,ESX", "gce,GCE", "hyperv,HYPERV", "kvm,KVM"})
    void fromWire_shouldReturnConstant_whenKnownValue(
            final String wire, final GitHubManageVersionVersionPlatform value) {
        final GitHubManageVersionVersionPlatform decoded = GitHubManageVersionVersionPlatform.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubManageVersionVersionPlatform.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubManageVersionVersionPlatform decoded = GitHubManageVersionVersionPlatform.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubManageVersionVersionPlatform.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
