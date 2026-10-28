package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubForkEventForkeeTest {

    @Test
    void constructor_shouldLeaveTopicsNull_whenNull() {
        final GitHubForkEventForkee value = GitHubForkEventForkeeBuilder.builder(
                        ActivityCatalogFixtures.forkEventForkee())
                .topics(null)
                .build();

        assertThat(value.topics()).isNull();
    }
}
