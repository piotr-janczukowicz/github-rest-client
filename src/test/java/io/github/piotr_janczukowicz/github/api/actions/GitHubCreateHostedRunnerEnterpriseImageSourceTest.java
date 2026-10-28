package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateHostedRunnerEnterpriseImageSourceTest {

    @Test
    void fromWire_shouldReturnGithub_whenValueGithub() {
        assertThat(GitHubCreateHostedRunnerEnterpriseImageSource.fromWire("github"))
                .isEqualTo(GitHubCreateHostedRunnerEnterpriseImageSource.GITHUB);
    }

    @Test
    void fromWire_shouldReturnPartner_whenValuePartner() {
        assertThat(GitHubCreateHostedRunnerEnterpriseImageSource.fromWire("partner"))
                .isEqualTo(GitHubCreateHostedRunnerEnterpriseImageSource.PARTNER);
    }

    @Test
    void fromWire_shouldReturnCustom_whenValueCustom() {
        assertThat(GitHubCreateHostedRunnerEnterpriseImageSource.fromWire("custom"))
                .isEqualTo(GitHubCreateHostedRunnerEnterpriseImageSource.CUSTOM);
    }

    @Test
    void wireValue_shouldReturnWireString_whenConstant() {
        assertThat(GitHubCreateHostedRunnerEnterpriseImageSource.GITHUB.wireValue())
                .isEqualTo("github");
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateHostedRunnerEnterpriseImageSource.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCreateHostedRunnerEnterpriseImageSource.fromWire("unknown-value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
