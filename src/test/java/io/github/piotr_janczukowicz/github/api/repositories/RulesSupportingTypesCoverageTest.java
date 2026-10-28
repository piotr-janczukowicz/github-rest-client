package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubListOrgRuleSuitesFilter;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubListOrgRuleSuitesFilterBuilder;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubListOrgRulesetsFilter;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubListOrgRulesetsFilterBuilder;
import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class RulesSupportingTypesCoverageTest {

    private static final String FIXTURE = "rules-coverage/";
    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void fromWire_shouldReturnEnforcement_whenDisabled() {
        final GitHubRepositoryRuleEnforcement decoded = GitHubRepositoryRuleEnforcement.fromWire("disabled");

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubRepositoryRuleEnforcement.DISABLED);
            softly.assertThat(decoded.wireValue()).isEqualTo("disabled");
        });
    }

    @Test
    void fromWire_shouldReturnEnforcement_whenActive() {
        final GitHubRepositoryRuleEnforcement decoded = GitHubRepositoryRuleEnforcement.fromWire("active");

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubRepositoryRuleEnforcement.ACTIVE);
            softly.assertThat(decoded.wireValue()).isEqualTo("active");
        });
    }

    @Test
    void fromWire_shouldReturnEnforcement_whenEvaluate() {
        final GitHubRepositoryRuleEnforcement decoded = GitHubRepositoryRuleEnforcement.fromWire("evaluate");

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubRepositoryRuleEnforcement.EVALUATE);
            softly.assertThat(decoded.wireValue()).isEqualTo("evaluate");
        });
    }

    @Test
    void fromWire_shouldThrow_whenEnforcementUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubRepositoryRuleEnforcement.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void empty_shouldReturnEmptyMap_whenListOrgRulesetsFilter() {
        final GitHubListOrgRulesetsFilter filter = GitHubListOrgRulesetsFilter.empty();

        final Map<String, String> params = filter.toQueryParameters();

        assertThat(params).isEmpty();
    }

    @Test
    void toQueryParameters_shouldReturnTargets_whenListOrgRulesetsFilterSet() {
        final GitHubListOrgRulesetsFilter filter = GitHubListOrgRulesetsFilterBuilder.builder()
                .targets("branch,tag")
                .build();

        final Map<String, String> params = filter.toQueryParameters();

        assertThat(params).containsEntry("targets", "branch,tag");
    }

    @Test
    void toQueryParameters_shouldOmitBlankTargets_whenListOrgRulesetsFilterTargetsBlank() {
        final GitHubListOrgRulesetsFilter filter =
                GitHubListOrgRulesetsFilterBuilder.builder().targets("   ").build();

        final Map<String, String> params = filter.toQueryParameters();

        assertThat(params).isEmpty();
    }

    @Test
    void empty_shouldReturnEmptyMap_whenGetRepoRulesetFilter() {
        final GitHubGetRepoRulesetFilter filter = GitHubGetRepoRulesetFilter.empty();

        final Map<String, String> params = filter.toQueryParameters();

        assertThat(params).isEmpty();
    }

    @Test
    void toQueryParameters_shouldReturnIncludesParents_whenGetRepoRulesetFilterSet() {
        final GitHubGetRepoRulesetFilter filter = GitHubGetRepoRulesetFilterBuilder.builder()
                .includesParents(true)
                .build();

        final Map<String, String> params = filter.toQueryParameters();

        assertThat(params).containsOnly(Map.entry("includes_parents", "true"));
    }

    @Test
    void empty_shouldReturnEmptyMap_whenGetRepoRulesetsFilter() {
        final GitHubGetRepoRulesetsFilter filter = GitHubGetRepoRulesetsFilter.empty();

        final Map<String, String> params = filter.toQueryParameters();

        assertThat(params).isEmpty();
    }

    @Test
    void toQueryParameters_shouldReturnAllParams_whenGetRepoRulesetsFilterSet() {
        final GitHubGetRepoRulesetsFilter filter = GitHubGetRepoRulesetsFilterBuilder.builder()
                .includesParents(false)
                .targets("branch")
                .build();

        final Map<String, String> params = filter.toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(params).containsEntry("includes_parents", "false");
            softly.assertThat(params).containsEntry("targets", "branch");
        });
    }

    @Test
    void toQueryParameters_shouldReturnIncludesParentsOnly_whenGetRepoRulesetsFilterTargetsBlank() {
        final GitHubGetRepoRulesetsFilter filter = GitHubGetRepoRulesetsFilterBuilder.builder()
                .includesParents(true)
                .targets("   ")
                .build();

        final Map<String, String> params = filter.toQueryParameters();

        assertThat(params).containsOnly(Map.entry("includes_parents", "true"));
    }

    @Test
    void toQueryParameters_shouldReturnAllParams_whenGetRepoRuleSuitesFilterSet() {
        final GitHubGetRepoRuleSuitesFilter filter = GitHubGetRepoRuleSuitesFilterBuilder.builder()
                .ref("refs/heads/main")
                .timePeriod(GitHubRuleSuiteTimePeriod.WEEK)
                .actorName("octocat")
                .ruleSuiteResult(GitHubRuleSuiteListResult.PASS)
                .evaluateStatus(GitHubRuleSuiteEvaluateStatus.EVALUATE)
                .build();

        final Map<String, String> params = filter.toQueryParameters();

        assertThat(params).hasSize(5);
    }

    @Test
    void toQueryParameters_shouldReturnAllParams_whenListOrgRuleSuitesFilterSet() {
        final GitHubListOrgRuleSuitesFilter filter = GitHubListOrgRuleSuitesFilterBuilder.builder()
                .ref("refs/heads/main")
                .repositoryName("repo")
                .timePeriod(GitHubRuleSuiteTimePeriod.WEEK)
                .actorName("octocat")
                .ruleSuiteResult(GitHubRuleSuiteListResult.PASS)
                .evaluateStatus(GitHubRuleSuiteEvaluateStatus.EVALUATE)
                .build();

        final Map<String, String> params = filter.toQueryParameters();

        assertThat(params).hasSize(6);
    }

    @Test
    void constructor_shouldStoreName_whenUpdateRepoRulesetPayloadBuilt() {
        final GitHubUpdateRepoRulesetPayload payload =
                GitHubUpdateRepoRulesetPayloadBuilder.builder().name("ruleset").build();

        assertThat(payload.name()).isEqualTo("ruleset");
    }

    @Test
    void constructor_shouldCopyDsseEnvelope_whenCreateAttestationBundlePresent() {
        final GitHubCreateAttestationPayload.CreateAttestationBundle bundle =
                new GitHubCreateAttestationPayload.CreateAttestationBundle("type", null, Map.of("b", 2));

        assertSoftly(softly -> {
            softly.assertThat(bundle.dsseEnvelope()).containsEntry("b", 2);
            softly.assertThat(bundle.verificationMaterial()).isNull();
        });
    }

    @Test
    void constructor_shouldLeaveMapsNull_whenCreateAttestationBundleAbsent() {
        final GitHubCreateAttestationPayload.CreateAttestationBundle bundle =
                new GitHubCreateAttestationPayload.CreateAttestationBundle("type", null, null);

        assertSoftly(softly -> {
            softly.assertThat(bundle.verificationMaterial()).isNull();
            softly.assertThat(bundle.dsseEnvelope()).isNull();
        });
    }

    @Test
    void constructor_shouldCopyMaps_whenCreateAttestationBundlePresent() {
        final GitHubCreateAttestationPayload.CreateAttestationBundle bundle =
                new GitHubCreateAttestationPayload.CreateAttestationBundle("type", Map.of("a", 1), Map.of("b", 2));

        assertSoftly(softly -> {
            softly.assertThat(bundle.verificationMaterial()).containsEntry("a", 1);
            softly.assertThat(bundle.dsseEnvelope()).containsEntry("b", 2);
        });
    }

    @ParameterizedTest
    @MethodSource("nestedConditionFixtures")
    void fixture_shouldDecodeToExpected_whenNestedConditionMapped(
            final Class<?> type, final String fixture, final Object expected) {
        final byte[] json = FileUtils.readBytes(RulesSupportingTypesCoverageTest.class, fixture);

        final Object decoded = CODEC.decode(json, type);

        assertThat(decoded).isEqualTo(expected);
    }

    @Test
    void decode_shouldReturnTypedFields_whenRuleSuiteFixtureLoaded() {
        final byte[] json = FileUtils.readBytes(RulesSupportingTypesCoverageTest.class, "rule-suite-null-details.json");

        final GitHubRuleSuite suite = CODEC.decode(json, GitHubRuleSuite.class);

        assertThat(suite).isEqualTo(RepositoriesCatalogFixtures.ruleSuiteWithNullDetails());
    }

    private static Stream<Arguments> nestedConditionFixtures() {
        return Stream.of(
                Arguments.of(
                        GitHubRepositoryRulesetConditionsRepositoryIdTarget.class,
                        FIXTURE + "repository-ruleset-conditions-repository-id-target.json",
                        RulesCoverageFixtures.coverageRepositoryIdTarget()),
                Arguments.of(
                        GitHubRepositoryRulesetConditionsRepositoryNameTarget.class,
                        FIXTURE + "repository-ruleset-conditions-repository-name-target.json",
                        RulesCoverageFixtures.coverageRepositoryNameTarget()),
                Arguments.of(
                        GitHubRepositoryRulesetConditionsRepositoryPropertySpec.class,
                        FIXTURE + "repository-ruleset-conditions-repository-property-spec.json",
                        RulesCoverageFixtures.coverageRepositoryPropertySpec()),
                Arguments.of(
                        GitHubRepositoryRulesetConditionsRepositoryPropertyTarget.class,
                        FIXTURE + "repository-ruleset-conditions-repository-property-target.json",
                        RulesCoverageFixtures.coverageRepositoryPropertyTarget()),
                Arguments.of(
                        GitHubRepositoryRulesetLinks.class,
                        FIXTURE + "repository-ruleset-links.json",
                        RepositoriesCatalogFixtures.rulesetLinks()));
    }
}
