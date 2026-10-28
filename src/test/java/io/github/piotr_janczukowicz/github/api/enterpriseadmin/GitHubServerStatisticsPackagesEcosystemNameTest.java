package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsPackagesEcosystemNameTest {

    @Test
    void fromWire_shouldReturnNpm_whenKnownValue() {
        assertThat(GitHubServerStatisticsPackagesEcosystemName.fromWire("npm"))
                .isEqualTo(GitHubServerStatisticsPackagesEcosystemName.NPM);
    }

    @Test
    void fromWire_shouldReturnMaven_whenKnownValue() {
        assertThat(GitHubServerStatisticsPackagesEcosystemName.fromWire("maven"))
                .isEqualTo(GitHubServerStatisticsPackagesEcosystemName.MAVEN);
    }

    @Test
    void fromWire_shouldReturnDocker_whenKnownValue() {
        assertThat(GitHubServerStatisticsPackagesEcosystemName.fromWire("docker"))
                .isEqualTo(GitHubServerStatisticsPackagesEcosystemName.DOCKER);
    }

    @Test
    void fromWire_shouldReturnNuget_whenKnownValue() {
        assertThat(GitHubServerStatisticsPackagesEcosystemName.fromWire("nuget"))
                .isEqualTo(GitHubServerStatisticsPackagesEcosystemName.NUGET);
    }

    @Test
    void fromWire_shouldReturnRubygems_whenKnownValue() {
        assertThat(GitHubServerStatisticsPackagesEcosystemName.fromWire("rubygems"))
                .isEqualTo(GitHubServerStatisticsPackagesEcosystemName.RUBYGEMS);
    }

    @Test
    void fromWire_shouldReturnContainers_whenKnownValue() {
        assertThat(GitHubServerStatisticsPackagesEcosystemName.fromWire("containers"))
                .isEqualTo(GitHubServerStatisticsPackagesEcosystemName.CONTAINERS);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        assertThat(GitHubServerStatisticsPackagesEcosystemName.fromWire("not-a-known-value"))
                .isEqualTo(GitHubServerStatisticsPackagesEcosystemName.UNKNOWN);
    }
}
