package io.github.piotr_janczukowicz.github.api.meta;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubApiOverviewTest {

    @Test
    void constructor_shouldKeepNullLists_whenAbsent() {
        final GitHubApiOverview overview = GitHubApiOverviewBuilder.builder().build();

        assertSoftly(softly -> {
            softly.assertThat(overview.sshKeys()).isNull();
            softly.assertThat(overview.hooks()).isNull();
            softly.assertThat(overview.githubEnterpriseImporter()).isNull();
            softly.assertThat(overview.web()).isNull();
            softly.assertThat(overview.api()).isNull();
            softly.assertThat(overview.git()).isNull();
            softly.assertThat(overview.packages()).isNull();
            softly.assertThat(overview.pages()).isNull();
            softly.assertThat(overview.importer()).isNull();
            softly.assertThat(overview.actions()).isNull();
            softly.assertThat(overview.actionsMacos()).isNull();
            softly.assertThat(overview.codespaces()).isNull();
            softly.assertThat(overview.dependabot()).isNull();
            softly.assertThat(overview.copilot()).isNull();
            softly.assertThat(overview.commitSigningKeys()).isNull();
        });
    }
}
