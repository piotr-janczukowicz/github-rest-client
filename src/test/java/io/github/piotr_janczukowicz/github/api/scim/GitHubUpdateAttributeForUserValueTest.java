package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateAttributeForUserValueTest {

    @Test
    void text_shouldHoldOnlyText_whenValueGiven() {
        final GitHubUpdateAttributeForUserValue value = GitHubUpdateAttributeForUserValue.text("false");

        assertThat(value.text()).isEqualTo("false");
        assertThat(value.object()).isNull();
        assertThat(value.array()).isNull();
    }

    @Test
    void object_shouldHoldOnlyObject_whenValueGiven() {
        final GitHubUpdateAttributeForUserValue value =
                GitHubUpdateAttributeForUserValue.object(ScimCatalogFixtures.updateAttributeForUserValueObject());

        assertThat(value.object().givenName().value()).isEqualTo("Monalisa");
        assertThat(value.text()).isNull();
        assertThat(value.array()).isNull();
    }

    @Test
    void array_shouldHoldOnlyArray_whenValueGiven() {
        final GitHubUpdateAttributeForUserValue value = GitHubUpdateAttributeForUserValue.array(
                List.of(ScimCatalogFixtures.updateAttributeForUserValueEmail()));

        assertThat(value.array()).hasSize(1);
        assertThat(value.text()).isNull();
        assertThat(value.object()).isNull();
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenNoBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateAttributeForUserValue(null, null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one");
    }
}
