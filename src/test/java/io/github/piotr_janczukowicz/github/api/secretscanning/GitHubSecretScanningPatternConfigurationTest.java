package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningPatternConfigurationTest {

    @Test
    void constructor_shouldAcceptNullOverrides_whenNoOverridesPresent() {
        final GitHubSecretScanningPatternConfiguration configuration =
                new GitHubSecretScanningPatternConfiguration("version", null, null);

        assertSoftly(softly -> {
            softly.assertThat(configuration.patternConfigVersion()).isEqualTo("version");
            softly.assertThat(configuration.providerPatternOverrides()).isNull();
            softly.assertThat(configuration.customPatternOverrides()).isNull();
        });
    }
}
