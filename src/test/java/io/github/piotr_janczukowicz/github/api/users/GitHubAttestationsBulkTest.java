package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAttestationsBulkTest {

    @Test
    void constructor_shouldKeepNullFields_whenAbsent() {
        final GitHubAttestationsBulk bulk = new GitHubAttestationsBulk(null, null);

        assertSoftly(softly -> {
            softly.assertThat(bulk.attestationsSubjectDigests()).isNull();
            softly.assertThat(bulk.pageInfo()).isNull();
        });
    }
}
