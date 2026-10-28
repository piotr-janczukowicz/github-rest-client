package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeleteActionsCacheByKeyFilterTest {

    @Test
    void of_shouldReturnFilterWithOnlyKey_whenKeyGiven() {
        final GitHubDeleteActionsCacheByKeyFilter sut = GitHubDeleteActionsCacheByKeyFilter.of("cache-key");

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).containsExactlyEntriesOf(Map.of("key", "cache-key"));
    }

    @Test
    void toQueryParameters_shouldIncludeRef_whenSet() {
        final GitHubDeleteActionsCacheByKeyFilter sut =
                new GitHubDeleteActionsCacheByKeyFilter("cache-key", "refs/heads/main");

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).containsExactlyInAnyOrderEntriesOf(Map.of("key", "cache-key", "ref", "refs/heads/main"));
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenKeyNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDeleteActionsCacheByKeyFilter(null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("key");
    }
}
