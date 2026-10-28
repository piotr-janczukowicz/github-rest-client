package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.licenses.GitHubLicenseSimple;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubCodeOfConductSimple;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Community profile files.
 *
 * @param codeOfConduct Code of conduct
 * @param codeOfConductFile Code of conduct file
 * @param license License
 * @param contributing Contributing file
 * @param readme Readme file
 * @param issueTemplate Issue template file
 * @param pullRequestTemplate Pull request template file
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/community-profile/properties/files")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCommunityProfileFiles(
        OptionalNull<GitHubCodeOfConductSimple> codeOfConduct,
        OptionalNull<GitHubCommunityHealthFile> codeOfConductFile,
        OptionalNull<GitHubLicenseSimple> license,
        OptionalNull<GitHubCommunityHealthFile> contributing,
        OptionalNull<GitHubCommunityHealthFile> readme,
        OptionalNull<GitHubCommunityHealthFile> issueTemplate,
        OptionalNull<GitHubCommunityHealthFile> pullRequestTemplate) {

    public GitHubCommunityProfileFiles {
        requireNonNull(codeOfConduct, "codeOfConduct");
        requireNonNull(codeOfConductFile, "codeOfConductFile");
        requireNonNull(license, "license");
        requireNonNull(contributing, "contributing");
        requireNonNull(readme, "readme");
        requireNonNull(issueTemplate, "issueTemplate");
        requireNonNull(pullRequestTemplate, "pullRequestTemplate");
    }
}
