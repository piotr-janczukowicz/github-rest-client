package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrgRulesetConditions;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRefName;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrgRulesetConditionsSerializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void encode_shouldWriteNameBranchWithRefName_whenConditionsAreRepositoryName() {
        final GitHubOrgRulesetConditions conditions = OrganizationsCatalogFixtures.orgRulesetConditions();

        final String json = encode(conditions);

        assertThat(json).isEqualTo(readExpected("org-ruleset-conditions-repository-name-with-ref-name.json"));
    }

    @Test
    void encode_shouldWriteProperties_whenConditionsAreUnknown() {
        final GitHubOrgRulesetConditions conditions =
                new GitHubOrgRulesetConditions.Unknown(Map.of("organization_id", Map.of("ids", List.of(2))));

        final String json = encode(conditions);

        assertThat(json).isEqualTo("{\"organization_id\":{\"ids\":[2]}}");
    }

    @Test
    void encode_shouldOmitRefName_whenIdBranchHasNoRef() {
        final GitHubOrgRulesetConditions conditions = GitHubOrgRulesetConditions.byRepositoryId(
                null, new GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId(List.of(1_296_269L)));

        final String json = encode(conditions);

        assertThat(json).isEqualTo(readExpected("org-ruleset-conditions-repository-id-without-ref-name.json"));
    }

    @Test
    void encode_shouldWritePropertyBranchWithRefName_whenConditionsAreRepositoryProperty() {
        final GitHubOrgRulesetConditions conditions = GitHubOrgRulesetConditions.byRepositoryProperty(
                new GitHubRepositoryRulesetConditionsRefName(List.of("refs/heads/main"), List.of()),
                new GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty(List.of(), List.of()));

        final String json = encode(conditions);

        assertThat(json).isEqualTo(readExpected("org-ruleset-conditions-repository-property-with-ref-name.json"));
    }

    @Test
    void encode_shouldWriteIdBranchWithRefName_whenRefPresent() {
        final GitHubOrgRulesetConditions conditions = GitHubOrgRulesetConditions.byRepositoryId(
                new GitHubRepositoryRulesetConditionsRefName(List.of("~ALL"), List.of()),
                new GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId(List.of(1L)));

        final String json = encode(conditions);

        assertThat(json).isEqualTo(readExpected("org-ruleset-conditions-repository-id-with-ref-name.json"));
    }

    @Test
    void encode_shouldWriteNameBranchWithoutRefName_whenRefAbsent() {
        final GitHubOrgRulesetConditions conditions = GitHubOrgRulesetConditions.byRepositoryName(
                null,
                new GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName(
                        List.of("*"), List.of(), false));

        final String json = encode(conditions);

        assertThat(json).isEqualTo(readExpected("org-ruleset-conditions-repository-name-without-ref-name.json"));
    }

    @Test
    void encode_shouldWritePropertyBranchWithoutRefName_whenRefAbsent() {
        final GitHubOrgRulesetConditions conditions = GitHubOrgRulesetConditions.byRepositoryProperty(
                null,
                new GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty(List.of(), List.of()));

        final String json = encode(conditions);

        assertThat(json).isEqualTo(readExpected("org-ruleset-conditions-repository-property-without-ref-name.json"));
    }

    private static String encode(final Object value) {
        return new String(CODEC.encode(value), StandardCharsets.UTF_8);
    }

    private static String readExpected(final String fileName) {
        return FileUtils.readString(GitHubOrgRulesetConditionsSerializerTest.class, fileName);
    }
}
