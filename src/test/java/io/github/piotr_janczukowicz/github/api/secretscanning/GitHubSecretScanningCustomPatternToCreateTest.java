package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningCustomPatternToCreateTest {

    @Test
    void builder_shouldCopyLists_whenMatchListsProvided() {
        final GitHubSecretScanningCustomPatternToCreate pattern =
                GitHubSecretScanningCustomPatternToCreateBuilder.builder()
                        .name("My custom pattern")
                        .pattern("token-[0-9]+")
                        .mustMatch(List.of("prefix"))
                        .mustNotMatch(List.of("suffix"))
                        .build();

        assertSoftly(softly -> {
            softly.assertThat(pattern.mustMatch()).containsExactly("prefix");
            softly.assertThat(pattern.mustNotMatch()).containsExactly("suffix");
        });
    }

    @Test
    void builder_shouldLeaveListsNull_whenMatchListsOmitted() {
        final GitHubSecretScanningCustomPatternToCreate pattern =
                GitHubSecretScanningCustomPatternToCreateBuilder.builder()
                        .name("My custom pattern")
                        .pattern("token-[0-9]+")
                        .build();

        assertSoftly(softly -> {
            softly.assertThat(pattern.mustMatch()).isNull();
            softly.assertThat(pattern.mustNotMatch()).isNull();
        });
    }
}
