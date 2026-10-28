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
class GitHubCreateIssueFieldPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubCreateIssueFieldPayload(null, null, GitHubIssueFieldDataType.TEXT, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenDataTypeNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCreateIssueFieldPayload("Priority", null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("dataType");
    }

    @Test
    void constructor_shouldCopyOptions_whenOptionsProvided() {
        final GitHubCreateIssueFieldOption option = OrganizationsCatalogFixtures.createIssueFieldOption();
        final List<GitHubCreateIssueFieldOption> options = new ArrayList<>(List.of(option));
        final GitHubCreateIssueFieldPayload payload = GitHubCreateIssueFieldPayloadBuilder.builder()
                .name("Priority")
                .dataType(GitHubIssueFieldDataType.SINGLE_SELECT)
                .options(OptionalNull.of(options))
                .build();
        options.clear();

        assertSoftly(softly -> {
            softly.assertThat(payload.options().value()).containsExactly(option);
            softly.assertThat(catchThrowable(() -> payload.options().value().add(option)))
                    .isInstanceOf(UnsupportedOperationException.class);
        });
    }

    @Test
    void constructor_shouldLeaveOptionsNull_whenOptionsNull() {
        final GitHubCreateIssueFieldPayload payload = GitHubCreateIssueFieldPayloadBuilder.builder()
                .name("Priority")
                .dataType(GitHubIssueFieldDataType.TEXT)
                .build();

        assertThat(payload.options()).isNull();
    }

    @Test
    void constructor_shouldKeepJsonNullOptions_whenOptionalNullOfNull() {
        final GitHubCreateIssueFieldPayload payload = GitHubCreateIssueFieldPayloadBuilder.builder()
                .name("Priority")
                .dataType(GitHubIssueFieldDataType.TEXT)
                .options(OptionalNull.ofNull())
                .build();

        assertThat(payload.options().value()).isNull();
    }
}
