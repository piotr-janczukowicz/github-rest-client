package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIssueFieldTest {

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final Throwable thrown = catchThrowable(() ->
                new GitHubIssueField(1, null, "Priority", null, GitHubIssueFieldDataType.TEXT, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssueField(
                1, "IF_kwDNAd3NAZr", null, null, GitHubIssueFieldDataType.TEXT, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenDataTypeNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubIssueField(1, "IF_kwDNAd3NAZr", "Priority", null, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("dataType");
    }

    @Test
    void constructor_shouldCopyOptions_whenOptionsProvided() {
        final GitHubIssueFieldOption option = OrganizationsCatalogFixtures.issueFieldOption();
        final GitHubIssueField issueField = GitHubIssueFieldBuilder.builder()
                .id(512)
                .nodeId("IF_kwDNAd3NAZr")
                .name("Priority")
                .dataType(GitHubIssueFieldDataType.SINGLE_SELECT)
                .options(List.of(option))
                .build();

        assertThat(issueField.options()).containsExactly(option);
    }
}
