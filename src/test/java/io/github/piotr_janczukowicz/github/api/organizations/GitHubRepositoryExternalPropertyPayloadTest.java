package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryExternalPropertyPayloadTest {

    @Test
    void constructor_shouldThrow_whenRepositoryNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryExternalPropertyPayload(null, "production"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoryName");
    }

    @Test
    void constructor_shouldLeaveValueNull_whenNull() {
        final GitHubRepositoryExternalPropertyPayload payload =
                new GitHubRepositoryExternalPropertyPayload("Hello-World", null);

        assertThat(payload.value()).isNull();
    }
}
