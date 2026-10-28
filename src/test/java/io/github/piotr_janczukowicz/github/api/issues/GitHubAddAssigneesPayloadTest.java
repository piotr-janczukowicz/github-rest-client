package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddAssigneesPayloadTest {

    @Test
    void constructor_shouldCopyAssignees_whenPresent() {
        final List<GitHubAddAssigneesAssignee> assignees =
                new ArrayList<>(List.of(GitHubAddAssigneesAssignee.ofLogin("octocat")));

        final GitHubAddAssigneesPayload payload = new GitHubAddAssigneesPayload(assignees);
        assignees.clear();

        assertThat(payload.assignees()).containsExactly(GitHubAddAssigneesAssignee.ofLogin("octocat"));
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubAddAssigneesPayload payload = new GitHubAddAssigneesPayload(null);

        assertThat(payload.assignees()).isNull();
    }
}
