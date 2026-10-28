package io.github.piotr_janczukowicz.github.api.codespaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetCodespacesAccessPayloadTest {

    @Test
    void constructor_shouldThrow_whenVisibilityNull() {
        final GitHubSetCodespacesAccessPayloadBuilder builder = GitHubSetCodespacesAccessPayloadBuilder.builder(
                        CodespacesCatalogFixtures.setCodespacesAccessPayload())
                .visibility(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("visibility");
    }

    @Test
    void constructor_shouldThrow_whenSelectedUsernamesExceedMaxItems() {
        final List<String> usernames = new ArrayList<>();
        for (int index = 0; index < 101; index++) {
            usernames.add("user" + index);
        }
        final GitHubSetCodespacesAccessPayloadBuilder builder = GitHubSetCodespacesAccessPayloadBuilder.builder(
                        CodespacesCatalogFixtures.setCodespacesAccessPayload())
                .selectedUsernames(usernames);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("selectedUsernames");
    }

    @Test
    void constructor_shouldLeaveSelectedUsernamesNull_whenNull() {
        final GitHubSetCodespacesAccessPayloadBuilder builder = GitHubSetCodespacesAccessPayloadBuilder.builder(
                        CodespacesCatalogFixtures.setCodespacesAccessPayload())
                .selectedUsernames(null);

        final GitHubSetCodespacesAccessPayload sut = builder.build();

        assertThat(sut.selectedUsernames()).isNull();
    }
}
