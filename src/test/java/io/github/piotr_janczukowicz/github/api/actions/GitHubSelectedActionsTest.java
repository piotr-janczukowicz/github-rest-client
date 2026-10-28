package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSelectedActionsTest {

    @Test
    void constructor_shouldCopyPatternsAllowed_whenPatternsAllowedGiven() {
        final List<String> source =
                new ArrayList<>(ActionsCatalogFixtures.selectedActions().patternsAllowed());
        final int size = source.size();
        final GitHubSelectedActions selectedActions = GitHubSelectedActionsBuilder.builder(
                        ActionsCatalogFixtures.selectedActions())
                .patternsAllowed(source)
                .build();

        source.add(source.getFirst());
        final List<String> patternsAllowed = selectedActions.patternsAllowed();
        final String first = source.getFirst();

        final Throwable thrown = catchThrowable(() -> patternsAllowed.add(first));

        assertSoftly(softly -> {
            softly.assertThat(selectedActions.patternsAllowed()).hasSize(size);
            softly.assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
        });
    }

    @Test
    void constructor_shouldAcceptNullPatternsAllowed_whenPatternsAllowedNull() {
        final GitHubSelectedActions selectedActions = GitHubSelectedActionsBuilder.builder(
                        ActionsCatalogFixtures.selectedActions())
                .patternsAllowed(null)
                .build();

        assertThat(selectedActions.patternsAllowed()).isNull();
    }
}
