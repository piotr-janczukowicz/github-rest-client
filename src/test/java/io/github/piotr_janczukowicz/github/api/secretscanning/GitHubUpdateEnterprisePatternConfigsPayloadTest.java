package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterprisePatternConfigsPayloadTest {

    private static final String VERSION = "0ujsswThIGTUYm2K8FjOOfXtY1K";

    @Test
    void builder_shouldCopyLists_whenSettingsProvided() {
        final GitHubUpdateEnterprisePatternConfigsProviderPatternSetting providerSetting =
                GitHubUpdateEnterprisePatternConfigsProviderPatternSettingBuilder.builder()
                        .tokenType("GITHUB_PERSONAL_ACCESS_TOKEN")
                        .pushProtectionSetting(GitHubUpdateEnterprisePatternConfigsPushProtectionSetting.ENABLED)
                        .build();
        final GitHubUpdateEnterprisePatternConfigsCustomPatternSetting customSetting =
                GitHubUpdateEnterprisePatternConfigsCustomPatternSettingBuilder.builder()
                        .tokenType("cp_2")
                        .customPatternVersion(OptionalNull.of(VERSION))
                        .pushProtectionSetting(
                                GitHubUpdateEnterprisePatternConfigsCustomPatternPushProtectionSetting.ENABLED)
                        .build();
        final GitHubUpdateEnterprisePatternConfigsPayload payload =
                GitHubUpdateEnterprisePatternConfigsPayloadBuilder.builder()
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
        final GitHubUpdateEnterprisePatternConfigsPayload payload =
                new GitHubUpdateEnterprisePatternConfigsPayload(null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(payload.providerPatternSettings()).isNull();
            softly.assertThat(payload.customPatternSettings()).isNull();
        });
    }

    @Test
    void constructor_shouldCopyProviderPatternSettings_whenSourceMutatedAfterConstruction() {
        final GitHubUpdateEnterprisePatternConfigsProviderPatternSetting providerSetting =
                GitHubUpdateEnterprisePatternConfigsProviderPatternSettingBuilder.builder()
                        .tokenType("GITHUB_PERSONAL_ACCESS_TOKEN")
                        .pushProtectionSetting(GitHubUpdateEnterprisePatternConfigsPushProtectionSetting.ENABLED)
                        .build();
        final List<GitHubUpdateEnterprisePatternConfigsProviderPatternSetting> settings =
                new ArrayList<>(List.of(providerSetting));
        final GitHubUpdateEnterprisePatternConfigsPayload payload =
                new GitHubUpdateEnterprisePatternConfigsPayload(null, settings, null);
        settings.clear();
        final List<GitHubUpdateEnterprisePatternConfigsProviderPatternSetting> providerSettings =
                payload.providerPatternSettings();

        assertSoftly(softly -> {
            softly.assertThat(providerSettings).containsExactly(providerSetting);
            softly.assertThat(catchThrowable(() -> providerSettings.add(providerSetting)))
                    .isInstanceOf(UnsupportedOperationException.class);
        });
    }
}
