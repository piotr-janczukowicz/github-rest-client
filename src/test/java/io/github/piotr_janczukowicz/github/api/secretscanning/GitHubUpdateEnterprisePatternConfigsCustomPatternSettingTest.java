package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterprisePatternConfigsCustomPatternSettingTest {

    @Test
    void constructor_shouldAccept_whenCustomPatternVersionIsJsonNull() {
        final GitHubUpdateEnterprisePatternConfigsCustomPatternSetting setting =
                GitHubUpdateEnterprisePatternConfigsCustomPatternSettingBuilder.builder()
                        .tokenType("cp_2")
                        .customPatternVersion(OptionalNull.ofNull())
                        .pushProtectionSetting(
                                GitHubUpdateEnterprisePatternConfigsCustomPatternPushProtectionSetting.ENABLED)
                        .build();

        assertThat(setting.customPatternVersion().value()).isNull();
    }
}
