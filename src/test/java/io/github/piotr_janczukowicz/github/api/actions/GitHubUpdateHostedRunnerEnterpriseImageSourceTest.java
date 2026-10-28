package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateHostedRunnerEnterpriseImageSourceTest {

    @Test
    void fromWire_shouldReturnGithub_whenValueGithub() {
        assertThat(GitHubUpdateHostedRunnerEnterpriseImageSource.fromWire("github"))
                .isEqualTo(GitHubUpdateHostedRunnerEnterpriseImageSource.GITHUB);
    }

    @Test
    void fromWire_shouldReturnPartner_whenValuePartner() {
        assertThat(GitHubUpdateHostedRunnerEnterpriseImageSource.fromWire("partner"))
                .isEqualTo(GitHubUpdateHostedRunnerEnterpriseImageSource.PARTNER);
    }

    @Test
    void fromWire_shouldReturnCustom_whenValueCustom() {
        assertThat(GitHubUpdateHostedRunnerEnterpriseImageSource.fromWire("custom"))
                .isEqualTo(GitHubUpdateHostedRunnerEnterpriseImageSource.CUSTOM);
    }

    @Test
    void wireValue_shouldReturnWireString_whenConstant() {
        assertThat(GitHubUpdateHostedRunnerEnterpriseImageSource.GITHUB.wireValue())
                .isEqualTo("github");
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateHostedRunnerEnterpriseImageSource.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdateHostedRunnerEnterpriseImageSource.fromWire("unknown-value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
