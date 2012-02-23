package com.codeaffine.archive.ui.internal.editor;

import java.io.InputStream;

import org.eclipse.core.resources.IContainer;

import com.codeaffine.archive.testfixture.TestProject;


class TestArchiveFile {
  static final String ENTRY_FILE_NAME = "file.txt";

  static void provision( IContainer project, String fileName ) throws Exception {
    InputStream inputStream = TestArchiveFile.class.getResourceAsStream( "archive.zip" );
    try {
      TestProject.createFile( project, fileName, inputStream );
    } finally {
      inputStream.close();
    }
  }

  private TestArchiveFile() {
    // prevent instantiation
  }
}
