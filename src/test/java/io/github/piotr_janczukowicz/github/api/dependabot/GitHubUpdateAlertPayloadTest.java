package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateAlertPayloadTest {

    @Test
    void constructor_shouldCopyAssignees_whenMutableList() {
        final GitHubUpdateAlertPayload source = DependabotCatalogFixtures.updateAlertPayload();
        final int size = source.assignees().size();
        final List<String> mutable = new ArrayList<>(source.assignees());
        final GitHubUpdateAlertPayload sut = GitHubUpdateAlertPayloadBuilder.builder(source)
                .assignees(mutable)
                .build();
        mutable.clear();

        assertThat(sut.assignees()).hasSize(size);
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubUpdateAlertPayloadBuilder builder = GitHubUpdateAlertPayloadBuilder.builder(
                        DependabotCatalogFixtures.updateAlertPayload())
                .assignees(null);

        final GitHubUpdateAlertPayload sut = builder.build();

        assertThat(sut.assignees()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenNeitherStateNorAssigneesGiven() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateAlertPayload(null, null, null, null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("state or assignees");
    }

    @Test
    void constructor_shouldThrow_whenDismissedCommentTooLong() {
        final GitHubUpdateAlertPayloadBuilder builder = GitHubUpdateAlertPayloadBuilder.builder(
                        DependabotCatalogFixtures.updateAlertPayload())
                .dismissedComment("x".repeat(281));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dismissedComment");
    }
}
