package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCustomPropertySetPayloadTest {

    @Test
    void constructor_shouldThrow_whenValueTypeNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCustomPropertySetPayload(null, null, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("valueType");
    }

    @Test
    void constructor_shouldAcceptStringDefaultValue_whenProvided() {
        final GitHubCustomPropertySetPayload payload = GitHubCustomPropertySetPayloadBuilder.builder()
                .valueType(GitHubCustomPropertyValueType.STRING)
                .defaultValue(OptionalNull.of(List.of("production")))
                .allowedValues(OptionalNull.of(List.of("production")))
                .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.defaultValue().value()).containsExactly("production");
            softly.assertThat(payload.allowedValues().value()).containsExactly("production");
        });
    }

    @Test
    void constructor_shouldAcceptListDefaultValue_whenProvided() {
        final GitHubCustomPropertySetPayload payload = GitHubCustomPropertySetPayloadBuilder.builder()
                .valueType(GitHubCustomPropertyValueType.MULTI_SELECT)
                .defaultValue(OptionalNull.of(List.of("production", "development")))
                .build();

        assertThat(payload.defaultValue().value()).containsExactly("production", "development");
    }

    @Test
    void constructor_shouldLeaveListsNull_whenOptionalListsNull() {
        final GitHubCustomPropertySetPayload payload = GitHubCustomPropertySetPayloadBuilder.builder()
                .valueType(GitHubCustomPropertyValueType.STRING)
                .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.defaultValue()).isNull();
            softly.assertThat(payload.allowedValues()).isNull();
        });
    }

    @Test
    void constructor_shouldCopyAllowedValues_whenSourceMutatedAfterConstruction() {
        final List<String> allowedValues = new ArrayList<>(List.of("production"));
        final GitHubCustomPropertySetPayload payload = GitHubCustomPropertySetPayloadBuilder.builder()
                .valueType(GitHubCustomPropertyValueType.SINGLE_SELECT)
                .allowedValues(OptionalNull.of(allowedValues))
                .build();
        allowedValues.clear();

        assertSoftly(softly -> {
            softly.assertThat(payload.allowedValues().value()).containsExactly("production");
            softly.assertThat(
                            catchThrowable(() -> payload.allowedValues().value().add("development")))
                    .isInstanceOf(UnsupportedOperationException.class);
        });
    }

    @Test
    void constructor_shouldKeepJsonNullLists_whenOptionalNullOfNull() {
        final GitHubCustomPropertySetPayload payload = GitHubCustomPropertySetPayloadBuilder.builder()
                .valueType(GitHubCustomPropertyValueType.STRING)
                .defaultValue(OptionalNull.ofNull())
                .allowedValues(OptionalNull.ofNull())
                .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.defaultValue().value()).isNull();
            softly.assertThat(payload.allowedValues().value()).isNull();
        });
    }
}
