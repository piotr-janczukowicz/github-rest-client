package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDismissalRequestResponseReviewerTest {

    @Test
    void constructor_shouldKeepActorName_whenBuilt() {
        final GitHubDismissalRequestResponseReviewer sut =
                CodeScanningCatalogFixtures.dismissalRequestResponseReviewer();

        assertThat(sut.actorName()).isEqualTo("octocat");
    }
}
