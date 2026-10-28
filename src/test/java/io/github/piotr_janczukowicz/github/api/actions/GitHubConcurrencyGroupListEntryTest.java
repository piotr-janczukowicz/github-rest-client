package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubConcurrencyGroupListEntryTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenGroupNameNull() {
        final String groupUrl = "https://api.github.com/example";
        final Instant lastAcquiredAt = Instant.parse("2024-01-02T03:04:05Z");

        final Throwable thrown =
                catchThrowable(() -> new GitHubConcurrencyGroupListEntry(null, groupUrl, lastAcquiredAt));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("groupName");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenGroupUrlNull() {
        final String groupName = "groupName-sample";
        final Instant lastAcquiredAt = Instant.parse("2024-01-02T03:04:05Z");

        final Throwable thrown =
                catchThrowable(() -> new GitHubConcurrencyGroupListEntry(groupName, null, lastAcquiredAt));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("groupUrl");
    }
}
