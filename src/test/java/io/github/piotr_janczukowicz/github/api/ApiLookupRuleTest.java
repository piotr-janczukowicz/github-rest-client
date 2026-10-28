package io.github.piotr_janczukowicz.github.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

@UnitTest
class ApiLookupRuleTest {

    private static final Pattern TEMPLATE =
            Pattern.compile("PathTemplate (\\w+) =\\s*PathTemplate\\.of\\(\\s*((?:\"[^\"]*\"\\s*(?:\\+\\s*)?)+)\\)");
    private static final Pattern TEMPLATE_PART = Pattern.compile("\"([^\"]*)\"");
    private static final Pattern CALL =
            Pattern.compile("caller\\.(fetch|fetchOrEmpty)\\(\\s*(?:caller\\.\\w+\\(\\s*)?(?:super\\.)?get\\((\\w+)");
    private static final Pattern SIGNATURE =
            Pattern.compile("public\\s+(?:<[^>]+>\\s+)?([\\w<>?\\[\\].]+(?:[, ]+[\\w<>?\\[\\].]+)*)\\s+(\\w+)\\(");
    private static final Pattern OPTIONAL_COLLECTION =
            Pattern.compile("public\\s+(?:<[^>]+>\\s+)?Optional<(?:List|Map|Set|GitHubPage)<[^(]*\\(");
    private static final Pattern VARIABLE = Pattern.compile("\\{\\+?([\\w-]+)}");

    private static final Set<String> SCOPE = Set.of("owner", "repo", "org", "enterprise");

    private static final Set<String> OPTIONAL_WITHOUT_KEY = Set.of(
            "GitHubOrganizationApi#get",
            "GitHubRepositoryApi#get",
            "GitHubOrganizationsMembersApi#getMembershipForAuthenticatedUser",
            "GitHubLicensesApi#getForRepo",
            "GitHubReleaseApi#getLatestRelease",
            "GitHubRepositoriesContentsApi#getReadme",
            "GitHubRepositoriesContentsApi#getRootContent",
            "GitHubPagesApi#getPages",
            "GitHubMigrationsSourceImportsApi#getImportStatus",
            "GitHubActivityWatchingApi#getRepoSubscription",
            "GitHubRepositoryApi#codeownersErrors",
            "GitHubRateLimitApi#get");

    private static final Set<String> COLLECTION_WRAPPERS = Set.of(
            "GitHubActionsHostedRunnersApi#listCustomImageVersionsForOrg",
            "GitHubActionsHostedRunnersApi#listCustomImageVersionsForEnterprise",
            "GitHubOrganizationsArtifactMetadataApi#listArtifactDeploymentRecords",
            "GitHubOrganizationsArtifactMetadataApi#listArtifactStorageRecords",
            "GitHubPullsReviewRequestsApi#list");

    private static final Set<String> THROWING_WITH_KEY = Set.of("GitHubActionsSecretsApi#getEnvironmentPublicKey");

    @Test
    void api_shouldReturnOptionalExactlyForLookups_whenMethodGetsOneResource() throws IOException {
        final List<String> violations;
        try (Stream<Path> files = Files.walk(Path.of("src/main/java/io/github/piotr_janczukowicz/github/api"))) {
            violations = files.filter(path -> path.toString().endsWith("Api.java"))
                    .flatMap(ApiLookupRuleTest::violationsIn)
                    .toList();
        }

        assertThat(violations)
                .as("fetchOrEmpty for a lookup by key or a listed exception, fetch for everything else")
                .isEmpty();
    }

    @Test
    void api_shouldNeverReturnOptionalOfCollection_whenMethodListsResources() throws IOException {
        final List<String> violations;
        try (Stream<Path> files = Files.walk(Path.of("src/main/java/io/github/piotr_janczukowicz/github/api"))) {
            violations = files.filter(path -> path.toString().endsWith("Api.java"))
                    .flatMap(ApiLookupRuleTest::optionalCollectionsIn)
                    .toList();
        }

        assertThat(violations)
                .as("collections and pages are returned as they are, empty when there is nothing")
                .isEmpty();
    }

    @Test
    void api_shouldParseEveryPathTemplate_whenTemplatesAreDeclared() throws IOException {
        final List<String> unparsed;
        try (Stream<Path> files = Files.walk(Path.of("src/main/java/io/github/piotr_janczukowicz/github/api"))) {
            unparsed = files.filter(path -> path.toString().endsWith("Api.java"))
                    .flatMap(ApiLookupRuleTest::unparsedTemplatesIn)
                    .toList();
        }

        assertThat(unparsed)
                .as("every PathTemplate.of declaration must be read by the lookup rule")
                .isEmpty();
    }

    private static Stream<String> unparsedTemplatesIn(final Path path) {
        try {
            final String source = Files.readString(path, StandardCharsets.UTF_8);
            final long declared = source.split("PathTemplate\\.of\\(", -1).length - 1L;
            final Matcher parsed = TEMPLATE.matcher(source);
            long count = 0;
            while (parsed.find()) {
                count++;
            }
            return count == declared
                    ? Stream.empty()
                    : Stream.of(path.getFileName() + ": " + declared + " declared, " + count + " parsed");
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Stream<String> optionalCollectionsIn(final Path path) {
        try {
            final Matcher matcher = OPTIONAL_COLLECTION.matcher(Files.readString(path, StandardCharsets.UTF_8));
            final Stream.Builder<String> found = Stream.builder();
            while (matcher.find()) {
                found.add(path.getFileName() + ": " + matcher.group().replaceAll("\\s+", " "));
            }
            return found.build();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Stream<String> violationsIn(final Path path) {
        try {
            final String source = Files.readString(path, StandardCharsets.UTF_8);
            final String type = path.getFileName().toString().replace(".java", "");
            final Map<String, String> templates = new HashMap<>();
            final Matcher declared = TEMPLATE.matcher(source);
            while (declared.find()) {
                templates.put(declared.group(1), templateValue(declared.group(2)));
            }
            final Stream.Builder<String> found = Stream.builder();
            final Matcher calls = CALL.matcher(source);
            while (calls.find()) {
                final String template = templates.get(calls.group(2));
                final Signature signature = lastSignatureBefore(source, calls.start());
                if (template == null || signature == null) {
                    continue;
                }
                final String method = type + "#" + signature.name();
                if (isCollection(signature.returnType()) || COLLECTION_WRAPPERS.contains(method)) {
                    continue;
                }
                final boolean isOptional = "fetchOrEmpty".equals(calls.group(1));
                if (isOptional != isLookup(template, method)) {
                    found.add(method + " " + template + (isOptional ? " is Optional" : " throws on 404"));
                }
            }
            return found.build();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String templateValue(final String literals) {
        final StringBuilder value = new StringBuilder();
        final Matcher part = TEMPLATE_PART.matcher(literals);
        while (part.find()) {
            value.append(part.group(1));
        }
        return value.toString();
    }

    private static Signature lastSignatureBefore(final String source, final int position) {
        final Matcher matcher = SIGNATURE.matcher(source.substring(0, position));
        Signature last = null;
        while (matcher.find()) {
            last = new Signature(matcher.group(1), matcher.group(2));
        }
        return last;
    }

    private static boolean isCollection(final String returnType) {
        return returnType.startsWith("List<")
                || returnType.startsWith("GitHubPage<")
                || returnType.startsWith("Map<")
                || returnType.endsWith("Page");
    }

    private static boolean isLookup(final String template, final String method) {
        if (THROWING_WITH_KEY.contains(method)) {
            return false;
        }
        if (OPTIONAL_WITHOUT_KEY.contains(method)) {
            return true;
        }
        final Matcher variables = VARIABLE.matcher(template);
        while (variables.find()) {
            final String name = variables.group(1);
            final boolean isScope = SCOPE.contains(name) || "username".equals(name) && template.contains("/settings/");
            if (!isScope) {
                return true;
            }
        }
        return false;
    }

    private record Signature(String returnType, String name) {}
}
