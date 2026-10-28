package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationPatPermissionsTest {

    @Test
    void constructor_shouldKeepNullMaps_whenAbsent() {
        final GitHubOrganizationPatPermissions permissions = new GitHubOrganizationPatPermissions(null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(permissions.organization()).isNull();
            softly.assertThat(permissions.repository()).isNull();
            softly.assertThat(permissions.other()).isNull();
        });
    }
}
