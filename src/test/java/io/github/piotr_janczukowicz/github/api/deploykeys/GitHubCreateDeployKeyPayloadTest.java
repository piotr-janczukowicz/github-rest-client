package io.github.piotr_janczukowicz.github.api.deploykeys;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateDeployKeyPayloadTest {

    @Test
    void constructor_shouldThrow_whenKeyNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateDeployKeyPayloadBuilder.builder().key(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("key");
    }
}
