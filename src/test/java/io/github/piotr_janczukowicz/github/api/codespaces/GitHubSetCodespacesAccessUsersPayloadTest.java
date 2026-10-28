package io.github.piotr_janczukowicz.github.api.codespaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetCodespacesAccessUsersPayloadTest {

    @Test
    void constructor_shouldThrow_whenSelectedUsernamesNull() {
        final GitHubSetCodespacesAccessUsersPayloadBuilder builder =
                GitHubSetCodespacesAccessUsersPayloadBuilder.builder(
                                CodespacesCatalogFixtures.setCodespacesAccessUsersPayload())
                        .selectedUsernames(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("selectedUsernames");
    }

    @Test
    void constructor_shouldThrow_whenSelectedUsernamesExceedMaxItems() {
        final List<String> usernames = new ArrayList<>();
        for (int index = 0; index < 101; index++) {
            usernames.add("user" + index);
        }
        final GitHubSetCodespacesAccessUsersPayloadBuilder builder =
                GitHubSetCodespacesAccessUsersPayloadBuilder.builder(
                                CodespacesCatalogFixtures.setCodespacesAccessUsersPayload())
                        .selectedUsernames(usernames);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("selectedUsernames");
    }

    @Test
    void constructor_shouldCopySelectedUsernames_whenMutableList() {
        final List<String> mutable = new ArrayList<>(List.of("johnDoe"));
        final GitHubSetCodespacesAccessUsersPayload sut = GitHubSetCodespacesAccessUsersPayloadBuilder.builder(
                        CodespacesCatalogFixtures.setCodespacesAccessUsersPayload())
                .selectedUsernames(mutable)
                .build();

        mutable.clear();

        assertThat(sut.selectedUsernames()).containsExactly("johnDoe");
    }
}
