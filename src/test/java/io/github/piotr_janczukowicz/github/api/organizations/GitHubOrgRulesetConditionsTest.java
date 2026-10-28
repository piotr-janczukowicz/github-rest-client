package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRefName;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrgRulesetConditionsTest {

    @Test
    void byRepositoryName_shouldThrow_whenRepositoryNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubOrgRulesetConditions.byRepositoryName(
                new GitHubRepositoryRulesetConditionsRefName(List.of("refs/heads/main"), List.of()), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoryName");
    }

    @Test
    void byRepositoryId_shouldThrow_whenRepositoryIdNull() {
        final Throwable thrown = catchThrowable(() -> GitHubOrgRulesetConditions.byRepositoryId(null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoryId");
    }

    @Test
    void byRepositoryProperty_shouldThrow_whenRepositoryPropertyNull() {
        final Throwable thrown = catchThrowable(() -> GitHubOrgRulesetConditions.byRepositoryProperty(null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoryProperty");
    }

    @Test
    void byRepositoryName_shouldKeepRefName_whenPresent() {
        final GitHubOrgRulesetConditions.RepositoryNameAndRefName conditions =
                GitHubOrgRulesetConditions.byRepositoryName(
                        new GitHubRepositoryRulesetConditionsRefName(List.of("refs/heads/main"), List.of()),
                        RepositoriesCatalogFixtures.repositoryNameConditions());

        assertThat(conditions.refName().include()).containsExactly("refs/heads/main");
        assertThat(conditions.repositoryName().include()).containsExactly("Hello-World");
    }

    @Test
    void byRepositoryId_shouldAllowNullRefName_whenPolicyRuleset() {
        final GitHubOrgRulesetConditions.RepositoryIdAndRefName conditions = GitHubOrgRulesetConditions.byRepositoryId(
                null, new GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId(List.of(1_296_269L)));

        assertThat(conditions.refName()).isNull();
        assertThat(conditions.repositoryId().repositoryIds()).containsExactly(1_296_269L);
    }

    @Test
    void byRepositoryProperty_shouldKeepProperty_whenPresent() {
        final GitHubOrgRulesetConditions.RepositoryPropertyAndRefName conditions =
                GitHubOrgRulesetConditions.byRepositoryProperty(
                        null,
                        new GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty(
                                List.of(), List.of()));

        assertThat(conditions.repositoryProperty().include()).isEmpty();
    }
}
