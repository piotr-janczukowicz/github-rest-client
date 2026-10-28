package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsCacheUsageByRepositoryTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenFullNameNull() {
        final int activeCachesSizeInBytes = 1;
        final int activeCachesCount = 1;

        final Throwable thrown = catchThrowable(
                () -> new GitHubActionsCacheUsageByRepository(null, activeCachesSizeInBytes, activeCachesCount));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("fullName");
    }
}
