package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestStackBaseTest {

    @Test
    void constructor_shouldRetainFields_whenProvided() {
        final GitHubPullRequestStackBase base =
                new GitHubPullRequestStackBase("main", "6dcb09b5b57875f334f61aebed695e2e4193db5e");

        assertSoftly(softly -> {
            softly.assertThat(base).hasNoNullFieldsOrProperties();
            softly.assertThat(base.ref()).isEqualTo("main");
            softly.assertThat(base.sha()).isEqualTo("6dcb09b5b57875f334f61aebed695e2e4193db5e");
        });
    }
}
