package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubArtifactListedStorageRecordsTest {

    @Test
    void constructor_shouldKeepNullFields_whenAbsent() {
        final GitHubArtifactListedStorageRecords page = new GitHubArtifactListedStorageRecords(null, null);

        assertThat(page.totalCount()).isNull();
        assertThat(page.storageRecords()).isNull();
    }
}
