package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateProjectItemFieldPayloadTest {

    @Test
    void constructor_shouldThrow_whenValueWrapperNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateProjectItemFieldPayload(1, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void constructor_shouldAcceptNullClear_whenCalled() {
        final GitHubUpdateProjectItemFieldPayload field =
                new GitHubUpdateProjectItemFieldPayload(1, OptionalNull.ofNull());

        assertThat(field.value().value()).isNull();
    }
}
