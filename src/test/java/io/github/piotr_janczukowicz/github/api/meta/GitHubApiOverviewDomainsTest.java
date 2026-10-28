package io.github.piotr_janczukowicz.github.api.meta;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubApiOverviewDomainsTest {

    @Test
    void constructor_shouldKeepNullLists_whenAbsent() {
        final GitHubApiOverviewDomains domains =
                GitHubApiOverviewDomainsBuilder.builder().build();

        assertSoftly(softly -> {
            softly.assertThat(domains.website()).isNull();
            softly.assertThat(domains.codespaces()).isNull();
            softly.assertThat(domains.copilot()).isNull();
            softly.assertThat(domains.packages()).isNull();
            softly.assertThat(domains.storage()).isNull();
            softly.assertThat(domains.actions()).isNull();
        });
    }
}
