package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateStatusCheckProtectionPayloadTest {

    @Test
    void constructor_shouldLeaveListsNull_whenListsNull() {
        final GitHubUpdateStatusCheckProtectionPayload payload =
                GitHubUpdateStatusCheckProtectionPayloadBuilder.builder()
                        .strict(true)
                        .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.contexts()).isNull();
            softly.assertThat(payload.checks()).isNull();
        });
    }

    @Test
    void constructor_shouldCopyLists_whenListsProvided() {
        final GitHubUpdateStatusCheckProtectionPayload payload =
                GitHubUpdateStatusCheckProtectionPayloadBuilder.builder()
                        .strict(true)
                        .contexts(List.of("ci"))
                        .checks(List.of(BranchesCatalogFixtures.updateBranchProtectionRequiredStatusCheck()))
                        .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.contexts()).containsExactly("ci");
            softly.assertThat(payload.checks()).hasSize(1);
        });
    }
}
