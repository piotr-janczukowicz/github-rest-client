package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubConcurrencyGroupRunListEntryTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenGroupNameNull() {
        final String groupUrl = "https://api.github.com/example";
        final List<GitHubConcurrencyGroupRunMember> groupMembers =
                List.of(ActionsCatalogFixtures.concurrencyGroupRunMember());

        final Throwable thrown =
                catchThrowable(() -> new GitHubConcurrencyGroupRunListEntry(null, groupUrl, groupMembers));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("groupName");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenGroupUrlNull() {
        final String groupName = "groupName-sample";
        final List<GitHubConcurrencyGroupRunMember> groupMembers =
                List.of(ActionsCatalogFixtures.concurrencyGroupRunMember());

        final Throwable thrown =
                catchThrowable(() -> new GitHubConcurrencyGroupRunListEntry(groupName, null, groupMembers));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("groupUrl");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenGroupMembersNull() {
        final String groupName = "groupName-sample";
        final String groupUrl = "https://api.github.com/example";

        final Throwable thrown =
                catchThrowable(() -> new GitHubConcurrencyGroupRunListEntry(groupName, groupUrl, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("groupMembers");
    }
}
