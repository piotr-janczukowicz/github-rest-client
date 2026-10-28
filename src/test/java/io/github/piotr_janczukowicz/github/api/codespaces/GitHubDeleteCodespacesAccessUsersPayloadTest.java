package io.github.piotr_janczukowicz.github.api.codespaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeleteCodespacesAccessUsersPayloadTest {

    @Test
    void constructor_shouldThrow_whenSelectedUsernamesNull() {
        final GitHubDeleteCodespacesAccessUsersPayloadBuilder builder =
                GitHubDeleteCodespacesAccessUsersPayloadBuilder.builder(
                                CodespacesCatalogFixtures.deleteCodespacesAccessUsersPayload())
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
        final GitHubDeleteCodespacesAccessUsersPayloadBuilder builder =
                GitHubDeleteCodespacesAccessUsersPayloadBuilder.builder(
                                CodespacesCatalogFixtures.deleteCodespacesAccessUsersPayload())
                        .selectedUsernames(usernames);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("selectedUsernames");
    }

    @Test
    void constructor_shouldCopySelectedUsernames_whenMutableList() {
        final List<String> mutable = new ArrayList<>(List.of("johnDoe"));
        final GitHubDeleteCodespacesAccessUsersPayload sut = GitHubDeleteCodespacesAccessUsersPayloadBuilder.builder(
                        CodespacesCatalogFixtures.deleteCodespacesAccessUsersPayload())
                .selectedUsernames(mutable)
                .build();

        mutable.clear();

        assertThat(sut.selectedUsernames()).containsExactly("johnDoe");
    }
}
