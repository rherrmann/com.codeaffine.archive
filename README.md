# Archive Utilities for Eclipse [![Build Status](https://travis-ci.org/rherrmann/com.codeaffine.archive.png)](https://travis-ci.org/rherrmann/com.codeaffine.archive)
The Archive Utilities extend the Eclipse IDE to view the structure and content and extract zip archive files within the workspace. Zip-compatible archives like JARs, WARs, etc. are of course also supported.

This document is targeted at developers. For user documentation, pease read [this document](http://rherrmann.github.com/com.codeaffine.archive/index.html)

## Repository Contents

### Code 
The bundles `com.codeaffine.archive.contenttype` and `com.codeaffine.archive.ui` contain all the functionality.

### Tests
Each working code bundle has an accompanying fragment (with the suffix `.test`) that contains JUnit tests.
In addition, there are integration tests, located in the `com.codeaffine.archive.ui.itest` bundle. These depend on [SWTBot](http://eclipse.org/swtbot) and are also run as unit tests with JUnit.
Common helper and fixture code for all tests is in `com.codeaffine.archive.testfixture`.

The `com.codeaffine.archive.alltest` bundle contains a test suite that references all unit tests (not the integration tests) and should be used to run all tests from within the IDE.

### Target Platform
The `com.codeaffine.archive.releng` project contains [target platform](http://help.eclipse.org/indigo/index.jsp?topic=%2Forg.eclipse.pde.doc.user%2Fconcepts%2Ftarget.htm) definitions for Eclipse 3.6 and 3.7 that should be used for development. The build (see below) can be executed for further targets.

### Build
The `com.codeaffine.archive.releng` project contains everything that is necessary to build the software. The build is based on [Eclipse Tycho](http://www.eclipse.org/tycho). To run the build

    cd <git-repo>/com.codeaffine.archive.releng
    mvn clean install -P <eclipse-platform>
    
&lt;eclipse-platform&gt; can be one of `eclipse-3.6`, `eclipse-3.7`, `eclipse-3.8`, `eclipse-4.2`. If you omit the -P (maven profile) command line argument, the default `eclipse-3.7` is taken.

## Contributing
Please see the [contributing guidelines](CONTRIBUTING.md).

## License
The code is published under the terms of the [Eclipse Public License, version 1.0](https://www.eclipse.org/legal/epl-v10.html).
