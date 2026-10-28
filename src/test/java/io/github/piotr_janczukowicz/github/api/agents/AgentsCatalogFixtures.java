package io.github.piotr_janczukowicz.github.api.agents;

import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import java.time.Instant;
import java.util.List;

public final class AgentsCatalogFixtures {

    private static final String ENCRYPTED_VALUE = "c2VjcmV0";
    private static final String KEY_ID = "012345678912345678";
    private static final Instant SECRET_CREATED_AT = Instant.parse("2019-08-10T14:59:22Z");
    private static final Instant SECRET_UPDATED_AT = Instant.parse("2020-01-10T14:59:22Z");
    private static final Instant VARIABLE_CREATED_AT = Instant.parse("2019-01-24T22:45:36Z");

    private AgentsCatalogFixtures() {}

    public static GitHubActionsPublicKey actionsPublicKey() {
        return new GitHubActionsPublicKey(
                KEY_ID,
                "hBT5WZEj8ZoOv6TYJsfWq7MxTEQopZO5/IT3ZCVQPzs=",
                2L,
                "https://api.github.com/user/keys/2",
                "ssh-rsa AAAAB3NzaC1yc2EAAA",
                "2011-01-26T19:01:12Z");
    }

    public static GitHubActionsSecret actionsSecret() {
        return new GitHubActionsSecret("SECRET_TOKEN", SECRET_CREATED_AT, SECRET_UPDATED_AT);
    }

    public static GitHubActionsVariable actionsVariable() {
        return new GitHubActionsVariable("USERNAME", "octocat", VARIABLE_CREATED_AT, VARIABLE_CREATED_AT);
    }

    static GitHubActionsSecretsPage actionsSecretsPage() {
        return new GitHubActionsSecretsPage(1, List.of(actionsSecret()));
    }

    static GitHubActionsVariablesPage actionsVariablesPage() {
        return new GitHubActionsVariablesPage(1, List.of(actionsVariable()));
    }

    static GitHubRepoOrganizationSecretsPage repoOrganizationSecretsPage() {
        return new GitHubRepoOrganizationSecretsPage(1, List.of(actionsSecret()));
    }

    static GitHubRepoOrganizationVariablesPage repoOrganizationVariablesPage() {
        return new GitHubRepoOrganizationVariablesPage(1, List.of(actionsVariable()));
    }

    static GitHubOrgActionsSecretsPage orgActionsSecretsPage() {
        return new GitHubOrgActionsSecretsPage(1, List.of(OrganizationsCatalogFixtures.organizationActionsSecret()));
    }

    static GitHubOrgActionsVariablesPage orgActionsVariablesPage() {
        return new GitHubOrgActionsVariablesPage(
                1, List.of(OrganizationsCatalogFixtures.organizationActionsVariable()));
    }

    static GitHubOrgSecretSelectedRepositoriesPage orgSecretSelectedRepositoriesPage() {
        return new GitHubOrgSecretSelectedRepositoriesPage(
                1, List.of(RepositoriesCatalogFixtures.helloWorldMinimalRepository()));
    }

    static GitHubOrgVariableSelectedRepositoriesPage orgVariableSelectedRepositoriesPage() {
        return new GitHubOrgVariableSelectedRepositoriesPage(
                1, List.of(RepositoriesCatalogFixtures.helloWorldMinimalRepository()));
    }

    static GitHubCreateOrUpdateOrgSecretPayload createOrUpdateOrgSecretPayload() {
        return new GitHubCreateOrUpdateOrgSecretPayload(
                ENCRYPTED_VALUE, KEY_ID, GitHubOrgSecretVisibility.SELECTED, List.of(1_296_269L, 1_296_280L));
    }

    static GitHubSetSelectedReposForOrgSecretPayload setSelectedReposForOrgSecretPayload() {
        return new GitHubSetSelectedReposForOrgSecretPayload(List.of(64_780_797L));
    }

    static GitHubCreateOrgVariablePayload createOrgVariablePayload() {
        return new GitHubCreateOrgVariablePayload(
                "USERNAME", "octocat", GitHubOrgVariableVisibility.SELECTED, List.of(1_296_269L, 1_296_280L));
    }

    static GitHubUpdateOrgVariablePayload updateOrgVariablePayload() {
        return new GitHubUpdateOrgVariablePayload(
                "USERNAME", "octocat", GitHubOrgVariableVisibility.SELECTED, List.of(1_296_269L, 1_296_280L));
    }

    static GitHubSetSelectedReposForOrgVariablePayload setSelectedReposForOrgVariablePayload() {
        return new GitHubSetSelectedReposForOrgVariablePayload(List.of(64_780_797L));
    }

    static GitHubCreateOrUpdateRepoSecretPayload createOrUpdateRepoSecretPayload() {
        return new GitHubCreateOrUpdateRepoSecretPayload(ENCRYPTED_VALUE, KEY_ID);
    }

    static GitHubCreateRepoVariablePayload createRepoVariablePayload() {
        return new GitHubCreateRepoVariablePayload("USERNAME", "octocat");
    }

    static GitHubUpdateRepoVariablePayload updateRepoVariablePayload() {
        return new GitHubUpdateRepoVariablePayload("USERNAME", "octocat");
    }
}
