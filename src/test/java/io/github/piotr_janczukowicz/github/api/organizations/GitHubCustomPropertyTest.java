package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCustomPropertyTest {

    @Test
    void constructor_shouldThrow_whenPropertyNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCustomProperty(
                null, null, null, GitHubCustomPropertyValueType.STRING, null, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("propertyName");
    }

    @Test
    void constructor_shouldThrow_whenValueTypeNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubCustomProperty("environment", null, null, null, null, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("valueType");
    }

    @Test
    void constructor_shouldCopyDefaultValue_whenProvided() {
        final List<String> defaultValue = new ArrayList<>(List.of("production"));

        final GitHubCustomProperty property = GitHubCustomPropertyBuilder.builder()
                .propertyName("environment")
                .valueType(GitHubCustomPropertyValueType.STRING)
                .defaultValue(defaultValue)
                .allowedValues(List.of("production"))
                .build();
        defaultValue.add("mutated");

        assertSoftly(softly -> {
            softly.assertThat(property.defaultValue()).containsExactly("production");
            softly.assertThat(property.allowedValues()).containsExactly("production");
        });
    }

    @Test
    void constructor_shouldAcceptListDefaultValue_whenProvided() {
        final GitHubCustomProperty property = GitHubCustomPropertyBuilder.builder()
                .propertyName("environment")
                .valueType(GitHubCustomPropertyValueType.MULTI_SELECT)
                .defaultValue(List.of("production", "development"))
                .build();

        assertThat(property.defaultValue()).containsExactly("production", "development");
    }
}
