package io.github.piotr_janczukowicz.github.api.codesecurity;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeSecurityConfigurationForRepositoryTest {

    @Test
    void accessors_shouldReturnValues_whenCreated() {
        final GitHubCodeSecurityConfigurationForRepository attachment =
                CodeSecurityCatalogFixtures.codeSecurityConfigurationForRepository();

        assertSoftly(softly -> {
            softly.assertThat(attachment.status()).isEqualTo(GitHubCodeSecurityAttachmentStatus.ATTACHED);
            softly.assertThat(attachment.configuration())
                    .isEqualTo(CodeSecurityCatalogFixtures.codeSecurityConfiguration());
        });
    }
}
