package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBulkCreateEnterpriseCustomPatternsResultTest {

    @Test
    void constructor_shouldLeaveCreatedPatternsNull_whenNull() {
        final GitHubBulkCreateEnterpriseCustomPatternsResult sut =
                new GitHubBulkCreateEnterpriseCustomPatternsResult(null);

        assertThat(sut.createdPatterns()).isNull();
    }
}
