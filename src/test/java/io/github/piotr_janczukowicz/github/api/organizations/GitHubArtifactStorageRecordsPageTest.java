package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubArtifactStorageRecordsPageTest {

    @Test
    void constructor_shouldKeepNullStorageRecords_whenAbsent() {
        final GitHubArtifactStorageRecordsPage page = new GitHubArtifactStorageRecordsPage(0, null);

        assertThat(page.storageRecords()).isNull();
    }
}
