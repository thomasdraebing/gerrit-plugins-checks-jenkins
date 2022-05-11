// Copyright (C) 2022 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.plugins.checks.jenkins;

import com.google.gerrit.extensions.annotations.PluginName;
import com.google.gerrit.extensions.restapi.Response;
import com.google.gerrit.extensions.restapi.RestReadView;
import com.google.gerrit.server.config.PluginConfigFactory;
import com.google.gerrit.server.project.NoSuchProjectException;
import com.google.gerrit.server.project.ProjectResource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jgit.lib.Config;

@Singleton
class GetConfig implements RestReadView<ProjectResource> {
  private static final String JENKINS_SECTION = "jenkins";
  private static final String JENKINS_URL_KEY = "url";
  private static final String JENKINS_JOB_KEY = "job";

  private final PluginConfigFactory config;
  private final String pluginName;

  @Inject
  GetConfig(PluginConfigFactory config, @PluginName String pluginName) {
    this.config = config;
    this.pluginName = pluginName;
  }

  @Override
  public Response<Set<JenkinsChecksConfig>> apply(ProjectResource project)
      throws NoSuchProjectException {
    Set<JenkinsChecksConfig> result = new HashSet<>();
    Config cfg = config.getProjectPluginConfig(project.getNameKey(), pluginName);
    for (String instance : cfg.getSubsections(JENKINS_SECTION)) {
      JenkinsChecksConfig jenkinsCfg = new JenkinsChecksConfig();
      jenkinsCfg.name = instance;
      jenkinsCfg.url = cfg.getString(JENKINS_SECTION, instance, JENKINS_URL_KEY);
      jenkinsCfg.jobs = cfg.getStringList(JENKINS_SECTION, instance, JENKINS_JOB_KEY);
      result.add(jenkinsCfg);
    }
    return Response.ok(result);
  }

  static class JenkinsChecksConfig {
    String name;
    String url;
    //TODO(Thomas): It would be preferable to not have to configure any jobs, but
    // to let Jenkins know which Jobs worked on a PatchSet. This will require
    // additional changes in Jenkins however.
    String[] jobs;
  }
}
