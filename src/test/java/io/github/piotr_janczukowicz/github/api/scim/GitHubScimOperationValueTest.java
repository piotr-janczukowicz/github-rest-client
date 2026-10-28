package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimOperationValueTest {

    @Test
    void text_shouldHoldOnlyText_whenValueGiven() {
        final GitHubScimOperationValue value = GitHubScimOperationValue.text("false");

        assertThat(value.text()).isEqualTo("false");
        assertThat(value.object()).isNull();
        assertThat(value.array()).isNull();
    }

    @Test
    void object_shouldHoldOnlyObject_whenValueGiven() {
        final GitHubScimOperationValue value = GitHubScimOperationValue.object(Map.of("active", false));

        assertThat(value.object()).containsEntry("active", false);
        assertThat(value.text()).isNull();
        assertThat(value.array()).isNull();
    }

    @Test
    void array_shouldHoldOnlyArray_whenValueGiven() {
        final GitHubScimOperationValue value =
                GitHubScimOperationValue.array(List.of(Map.of("value", "work@example.com")));

        assertThat(value.array()).hasSize(1);
        assertThat(value.text()).isNull();
        assertThat(value.object()).isNull();
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenNoBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubScimOperationValue(null, null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one");
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenTwoBranchesSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubScimOperationValue("false", null, List.of()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one");
    }
}
