package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateSecretScanningPatternConfigsPayloadTest {

    private static final String VERSION = "0ujsswThIGTUYm2K8FjOOfXtY1K";

    @Test
    void builder_shouldCopyLists_whenSettingsProvided() {
        final GitHubSecretScanningProviderPatternSetting providerSetting =
                GitHubSecretScanningProviderPatternSettingBuilder.builder()
                        .tokenType("GITHUB_PERSONAL_ACCESS_TOKEN")
                        .pushProtectionSetting(GitHubSecretScanningPatternPushProtectionSetting.ENABLED)
                        .build();
        final GitHubSecretScanningCustomPatternSetting customSetting =
                GitHubSecretScanningCustomPatternSettingBuilder.builder()
                        .tokenType("cp_2")
                        .customPatternVersion(OptionalNull.of(VERSION))
                        .pushProtectionSetting(GitHubSecretScanningCustomPatternPushProtectionSetting.ENABLED)
                        .build();
        final GitHubUpdateSecretScanningPatternConfigsPayload payload =
                GitHubUpdateSecretScanningPatternConfigsPayloadBuilder.builder()
                        .patternConfigVersion(OptionalNull.of(VERSION))
                        .providerPatternSettings(List.of(providerSetting))
                        .customPatternSettings(List.of(customSetting))
                        .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.providerPatternSettings()).containsExactly(providerSetting);
            softly.assertThat(payload.customPatternSettings()).containsExactly(customSetting);
        });
    }

    @Test
    void constructor_shouldKeepListsNull_whenListsNull() {
        final GitHubUpdateSecretScanningPatternConfigsPayload payload =
                new GitHubUpdateSecretScanningPatternConfigsPayload(null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(payload.providerPatternSettings()).isNull();
            softly.assertThat(payload.customPatternSettings()).isNull();
        });
    }

    @Test
    void constructor_shouldCopyProviderPatternSettings_whenSourceMutatedAfterConstruction() {
        final GitHubSecretScanningProviderPatternSetting providerSetting =
                GitHubSecretScanningProviderPatternSettingBuilder.builder()
                        .tokenType("GITHUB_PERSONAL_ACCESS_TOKEN")
                        .pushProtectionSetting(GitHubSecretScanningPatternPushProtectionSetting.ENABLED)
                        .build();
        final List<GitHubSecretScanningProviderPatternSetting> settings = new ArrayList<>(List.of(providerSetting));
        final GitHubUpdateSecretScanningPatternConfigsPayload payload =
                new GitHubUpdateSecretScanningPatternConfigsPayload(null, settings, null);
        settings.clear();
        final List<GitHubSecretScanningProviderPatternSetting> providerSettings = payload.providerPatternSettings();

        assertSoftly(softly -> {
            softly.assertThat(providerSettings).containsExactly(providerSetting);
            softly.assertThat(catchThrowable(() -> providerSettings.add(providerSetting)))
                    .isInstanceOf(UnsupportedOperationException.class);
        });
    }
}
