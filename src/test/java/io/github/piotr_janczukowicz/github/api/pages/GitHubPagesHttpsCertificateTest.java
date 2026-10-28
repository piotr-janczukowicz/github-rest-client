package io.github.piotr_janczukowicz.github.api.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPagesHttpsCertificateTest {

    @Test
    void constructor_shouldCopyDomains_whenPresent() {
        final GitHubPagesHttpsCertificate certificate = new GitHubPagesHttpsCertificate(
                GitHubPagesHttpsCertificateState.APPROVED, "ok", List.of("example.com"), null);

        assertThat(certificate.domains()).containsExactly("example.com");
    }

    @Test
    void constructor_shouldThrow_whenDomainsNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubPagesHttpsCertificate(GitHubPagesHttpsCertificateState.APPROVED, "ok", null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("domains");
    }
}
