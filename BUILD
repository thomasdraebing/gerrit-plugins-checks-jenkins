load("//tools/bzl:plugin.bzl", "gerrit_plugin")

package_group(
    name = "visibility",
    packages = ["//plugins/checks-jenkins/..."],
)

package(default_visibility = [":visibility"])

gerrit_plugin(
    name = "checks-jenkins",
    srcs = glob(["src/main/java/com/google/gerrit/plugins/checks/jenkins/**/*.java"]),
    manifest_entries = [
        "Gerrit-PluginName: checks-jenkins",
        "Gerrit-Module: com.google.gerrit.plugins.checks.jenkins.Module",
        "Gerrit-HttpModule: com.google.gerrit.plugins.checks.jenkins.HttpModule",
    ],
    resource_jars = ["//plugins/checks-jenkins/web:checks-jenkins"],
    resources = glob(["src/main/resources/**/*"]),
)
