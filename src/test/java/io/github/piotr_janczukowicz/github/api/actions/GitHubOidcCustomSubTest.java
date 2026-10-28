package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOidcCustomSubTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenIncludeClaimKeysNull() {
        final Boolean useImmutableSubject = true;

        final Throwable thrown = catchThrowable(() -> new GitHubOidcCustomSub(null, useImmutableSubject));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("includeClaimKeys");
    }
}
