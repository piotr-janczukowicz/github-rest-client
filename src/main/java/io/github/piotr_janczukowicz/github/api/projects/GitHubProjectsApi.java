package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Projects REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubProjectsApi {

    private final LazyValue<GitHubProjectApi> project;
    private final LazyValue<GitHubProjectsDraftsApi> drafts;
    private final LazyValue<GitHubProjectsFieldsApi> fields;
    private final LazyValue<GitHubProjectsItemsApi> items;
    private final LazyValue<GitHubProjectsViewsApi> views;

    /**
     * Projects resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubProjectsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.project = new LazyValue<>(() -> new GitHubProjectApi(caller));
        this.drafts = new LazyValue<>(() -> new GitHubProjectsDraftsApi(caller));
        this.fields = new LazyValue<>(() -> new GitHubProjectsFieldsApi(caller));
        this.items = new LazyValue<>(() -> new GitHubProjectsItemsApi(caller));
        this.views = new LazyValue<>(() -> new GitHubProjectsViewsApi(caller));
    }

    /**
     * Project operations ({@code subcategory=projects}).
     *
     * @return project API
     */
    public GitHubProjectApi project() {
        return project.get();
    }

    /**
     * Project draft item operations ({@code subcategory=drafts}).
     *
     * @return drafts API
     */
    public GitHubProjectsDraftsApi drafts() {
        return drafts.get();
    }

    /**
     * Project field operations ({@code subcategory=fields}).
     *
     * @return fields API
     */
    public GitHubProjectsFieldsApi fields() {
        return fields.get();
    }

    /**
     * Project item operations ({@code subcategory=items}).
     *
     * @return items API
     */
    public GitHubProjectsItemsApi items() {
        return items.get();
    }

    /**
     * Project view operations ({@code subcategory=views}).
     *
     * @return views API
     */
    public GitHubProjectsViewsApi views() {
        return views.get();
    }
}
