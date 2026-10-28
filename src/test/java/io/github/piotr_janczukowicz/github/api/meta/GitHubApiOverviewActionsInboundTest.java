package io.github.piotr_janczukowicz.github.api.meta;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubApiOverviewActionsInboundTest {

    @Test
    void constructor_shouldKeepNullLists_whenAbsent() {
        final GitHubApiOverviewActionsInbound inbound =
                GitHubApiOverviewActionsInboundBuilder.builder().build();

        assertSoftly(softly -> {
            softly.assertThat(inbound.fullDomains()).isNull();
            softly.assertThat(inbound.wildcardDomains()).isNull();
        });
    }
}
