package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateSecretScanningPatternConfigsResultTest {

    @Test
    void constructor_shouldAcceptNullPatternConfigVersion_whenPropertyOmitted() {
        final GitHubUpdateSecretScanningPatternConfigsResult result =
                new GitHubUpdateSecretScanningPatternConfigsResult(null);

        assertThat(result.patternConfigVersion()).isNull();
    }

    @Test
    void catalogSchema_shouldBindUpdateOperationResponse() {
        final CatalogSchema annotation =
                GitHubUpdateSecretScanningPatternConfigsResult.class.getAnnotation(CatalogSchema.class);

        assertThat(annotation.value()).containsExactly("operations/secret-scanning/update-org-pattern-configs/200");
    }
}
