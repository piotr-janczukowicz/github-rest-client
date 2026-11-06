package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterprisePatternConfigsResultTest {

    @Test
    void constructor_shouldAcceptNullPatternConfigVersion_whenPropertyOmitted() {
        final GitHubUpdateEnterprisePatternConfigsResult result = new GitHubUpdateEnterprisePatternConfigsResult(null);

        assertThat(result.patternConfigVersion()).isNull();
    }
}
