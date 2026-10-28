package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeOfConductSimpleTest {

    @Test
    void constructor_shouldRetainFields_whenHtmlUrlAbsent() {
        final GitHubCodeOfConductSimple codeOfConduct = new GitHubCodeOfConductSimple(
                "https://api.github.com/codes_of_conduct/contributor_covenant",
                "contributor_covenant",
                "Contributor Covenant",
                null);

        assertSoftly(softly -> {
            softly.assertThat(codeOfConduct).hasNoNullFieldsOrPropertiesExcept("htmlUrl");
            softly.assertThat(codeOfConduct.key()).isEqualTo("contributor_covenant");
            softly.assertThat(codeOfConduct.name()).isEqualTo("Contributor Covenant");
        });
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubCodeOfConductSimple(null, "contributor_covenant", "Contributor Covenant", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenKeyNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodeOfConductSimple(
                "https://api.github.com/codes_of_conduct/contributor_covenant", null, "Contributor Covenant", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("key");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodeOfConductSimple(
                "https://api.github.com/codes_of_conduct/contributor_covenant", "contributor_covenant", null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
