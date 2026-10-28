package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.projects.GitHubAddFieldForUserPayload;
import io.github.piotr_janczukowicz.github.api.projects.GitHubAddProjectFieldPayload;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectFieldDataType;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectFieldIterationConfiguration;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectFieldSingleSelectOption;
import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.LongFunction;
import org.jspecify.annotations.Nullable;

/** Shared variant ladder for add-project-field request bodies. */
final class GitHubProjectFieldPayloads {

    private static final String ISSUE_FIELD_ID = "issue_field_id";
    private static final String SINGLE_SELECT_OPTIONS = "single_select_options";
    private static final String ITERATION_CONFIGURATION = "iteration_configuration";
    private static final String NAME = "name";
    private static final String DATA_TYPE = "data_type";

    private GitHubProjectFieldPayloads() {
        throw new UnsupportedOperationException("Utility class");
    }

    static GitHubAddProjectFieldPayload readOrg(final BufferedJson json) {
        return read(
                json,
                GitHubAddProjectFieldPayload.IssueField::new,
                GitHubAddProjectFieldPayload.Basic::new,
                GitHubAddProjectFieldPayload.SingleSelect::new,
                GitHubAddProjectFieldPayload.Iteration::new);
    }

    static GitHubAddFieldForUserPayload readUser(final BufferedJson json) {
        if (json.hasField(ISSUE_FIELD_ID)) {
            throw json.mismatch("issue_field_id is not valid for add-field-for-user");
        }
        return read(
                json,
                null,
                GitHubAddProjectFieldPayload.Basic::new,
                GitHubAddProjectFieldPayload.SingleSelect::new,
                GitHubAddProjectFieldPayload.Iteration::new);
    }

    private static <T> T read(
            final BufferedJson json,
            final @Nullable LongFunction<T> issueField,
            final BiFunction<String, GitHubProjectFieldDataType, T> basic,
            final BiFunction<String, List<GitHubProjectFieldSingleSelectOption>, T> singleSelect,
            final BiFunction<String, GitHubProjectFieldIterationConfiguration, T> iteration) {
        if (issueField != null && json.hasNonNull(ISSUE_FIELD_ID)) {
            return issueField.apply(json.requiredLong(ISSUE_FIELD_ID));
        }
        if (json.hasNonNull(SINGLE_SELECT_OPTIONS)) {
            final GitHubProjectFieldSingleSelectOption[] options =
                    json.readRequiredField(SINGLE_SELECT_OPTIONS, GitHubProjectFieldSingleSelectOption[].class);
            return singleSelect.apply(json.requiredText(NAME), List.of(options));
        }
        if (json.hasNonNull(ITERATION_CONFIGURATION)) {
            return iteration.apply(
                    json.requiredText(NAME),
                    json.readRequiredField(ITERATION_CONFIGURATION, GitHubProjectFieldIterationConfiguration.class));
        }
        return basic.apply(json.requiredText(NAME), json.requiredWire(DATA_TYPE, GitHubProjectFieldDataType::fromWire));
    }
}
