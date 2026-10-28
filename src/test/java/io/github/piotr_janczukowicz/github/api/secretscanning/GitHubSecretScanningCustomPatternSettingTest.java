package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningCustomPatternSettingTest {

    @Test
    void catalogSchema_shouldBindUpdateOperationRequestItem() {
        final CatalogSchema annotation =
                GitHubSecretScanningCustomPatternSetting.class.getAnnotation(CatalogSchema.class);

        assertThat(annotation.value())
                .containsExactly(
                        "operations/secret-scanning/update-org-pattern-configs/requestBody/properties/custom_pattern_settings/items");
    }

    @Test
    void constructor_shouldAccept_whenCustomPatternVersionIsJsonNull() {
        final GitHubSecretScanningCustomPatternSetting setting =
                GitHubSecretScanningCustomPatternSettingBuilder.builder()
                        .tokenType("cp_2")
                        .customPatternVersion(OptionalNull.ofNull())
                        .pushProtectionSetting(GitHubSecretScanningCustomPatternPushProtectionSetting.ENABLED)
                        .build();

        assertThat(setting.customPatternVersion().value()).isNull();
    }
}
