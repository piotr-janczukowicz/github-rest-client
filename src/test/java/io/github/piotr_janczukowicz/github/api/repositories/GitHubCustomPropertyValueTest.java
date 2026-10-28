package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCustomPropertyValueTest {

    @Test
    void of_shouldStoreStringValue_whenCalled() {
        final GitHubCustomPropertyValue value = GitHubCustomPropertyValue.of("environment", "production");

        assertSoftly(softly -> {
            softly.assertThat(value.propertyName()).isEqualTo("environment");
            softly.assertThat(value.value()).isEqualTo(List.of("production"));
            softly.assertThat(value).hasNoNullFieldsOrProperties();
        });
    }

    @Test
    void of_shouldCopyStringList_whenCalled() {
        final List<String> values = new ArrayList<>(List.of("core", "platform"));

        final GitHubCustomPropertyValue value = GitHubCustomPropertyValue.of("team", values);
        values.add("mutated");

        assertThat(value.value()).isEqualTo(List.of("core", "platform"));
    }

    @Test
    void ofNull_shouldStoreNullValue_whenCalled() {
        final GitHubCustomPropertyValue value = GitHubCustomPropertyValue.ofNull("environment");

        assertSoftly(softly -> {
            softly.assertThat(value.value()).isNull();
            softly.assertThat(value).hasNoNullFieldsOrPropertiesExcept("value");
        });
    }

    @Test
    void constructor_shouldThrow_whenPropertyNameNull() {
        final List<String> values = List.of("production");

        final Throwable thrown = catchThrowable(() -> new GitHubCustomPropertyValue(null, values));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("propertyName");
    }

    @Test
    void of_shouldThrow_whenStringValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCustomPropertyValue.of("environment", (String) null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void of_shouldThrow_whenListValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCustomPropertyValue.of("environment", (List<String>) null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
