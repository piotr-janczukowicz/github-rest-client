package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOidcCustomPropertyInclusionTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenCustomPropertyNameNull() {
        final GitHubOidcCustomPropertyInclusionSource inclusionSource =
                GitHubOidcCustomPropertyInclusionSource.ORGANIZATION;

        final Throwable thrown = catchThrowable(() -> new GitHubOidcCustomPropertyInclusion(null, inclusionSource));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("customPropertyName");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenInclusionSourceNull() {
        final String customPropertyName = "customPropertyName-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubOidcCustomPropertyInclusion(customPropertyName, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("inclusionSource");
    }
}
