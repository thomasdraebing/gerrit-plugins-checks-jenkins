Implementation of checks UI for Jenkins CI servers

This plugin registers a `ChecksProvider` with the Gerrit UI that will fetch
build results for a change from configured Jenkins servers and provide them to
the checks panel in a change screen.

Limitations
-----------

Currently, only multibranch-pipeline jobs using the Gerrit SCM-source provided
by the link:https://plugins.jenkins.io/gerrit-code-review/[GerritCodeReview]-
plugin are supported.

The Jenkins Remote Access API does not provide all the information that could
be displayed in Gerrit, e.g. a result summary. Thus, as of now, this plugin
does not make full use of the checks API.
