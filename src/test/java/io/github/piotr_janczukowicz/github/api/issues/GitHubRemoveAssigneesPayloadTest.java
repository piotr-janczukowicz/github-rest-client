package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRemoveAssigneesPayloadTest {

    @Test
    void constructor_shouldCopyAssignees_whenPresent() {
        final List<String> assignees = new ArrayList<>(List.of("octocat"));

        final GitHubRemoveAssigneesPayload payload = new GitHubRemoveAssigneesPayload(assignees);
        assignees.clear();

        assertThat(payload.assignees()).containsExactly("octocat");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubRemoveAssigneesPayload payload = new GitHubRemoveAssigneesPayload(null);

        assertThat(payload.assignees()).isNull();
    }
}
