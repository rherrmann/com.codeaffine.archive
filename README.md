# Archive Utilities for Eclipse
The Archive Utilities extend the Eclipse IDE to view the structure and content and extract zip archive files within the workspace.

This document is targeted at developers. For user documentation, pease read [this document](http://rherrmann.github.com/com.codeaffine.archive/index.html)

## Repository Contents

### Code 
The bundles `com.codeaffine.archive.contenttype` and `com.codeaffine.archive.ui` contain all the functionality.

### Tests
Each working code bundle has an accomanying fragment (with the suffix `.test`) that contains JUnit tests.
The `com.codeaffine.archive.alltest` bundle contains a test suite that references all the tests from the fragments and should be used to run tests from the IDE.

### Build
The `com.codeaffine.archive.releng` project contains everything that is necessary to build the software.
