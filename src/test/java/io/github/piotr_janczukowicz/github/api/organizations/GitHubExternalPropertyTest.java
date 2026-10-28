package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubExternalPropertyTest {

    @Test
    void constructor_shouldThrow_whenPropertyNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubExternalProperty(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("propertyName");
    }
}
