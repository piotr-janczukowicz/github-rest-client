package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetSelfHostedRunnersInGroupForEnterprisePayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenRunnersNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSetSelfHostedRunnersInGroupForEnterprisePayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runners");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubSetSelfHostedRunnersInGroupForEnterprisePayload value =
                ActionsCatalogFixtures.setSelfHostedRunnersInGroupForEnterprisePayload();

        assertThat(value.runners()).containsExactly(1L);
    }
}
