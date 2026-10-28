package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningProviderPatternSettingTest {

    @Test
    void catalogSchema_shouldBindUpdateOperationRequestItem() {
        final CatalogSchema annotation =
                GitHubSecretScanningProviderPatternSetting.class.getAnnotation(CatalogSchema.class);

        assertThat(annotation.value())
                .containsExactly(
                        "operations/secret-scanning/update-org-pattern-configs/requestBody/properties/provider_pattern_settings/items");
    }
}
