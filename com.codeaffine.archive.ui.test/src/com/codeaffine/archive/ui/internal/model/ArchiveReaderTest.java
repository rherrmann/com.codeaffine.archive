package com.codeaffine.archive.ui.internal.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.codeaffine.archive.ui.internal.model.Archive;
import com.codeaffine.archive.ui.internal.model.ArchiveEntry;
import com.codeaffine.archive.ui.internal.model.ArchiveReader;
import com.codeaffine.archive.ui.internal.model.DirectoryEntry;
import com.codeaffine.archive.ui.internal.model.FileEntry;
import com.codeaffine.archive.ui.test.FileHelper;


public class ArchiveReaderTest {
  private static final String ARCHIVE_NAME = "archive-name";

  private File file;

  @Test
  public void testRead() throws Exception {
    provisionZipFile( "file.zip" );

    ArchiveReader archiveReader = createArchiveReader();
    Archive archive = archiveReader.read();

    assertEquals( ARCHIVE_NAME, archive.getName() );
    assertEquals( file.getCanonicalFile(), archive.getLocation().toFile().getCanonicalFile() );
  }

  @Test
  public void testRead_file_zip() throws Exception {
    provisionZipFile( "file.zip" );
    ArchiveReader archiveReader = createArchiveReader();

    Archive archive = archiveReader.read();
    ArchiveEntry[] archiveEntries = archive.getRootEntry().getChildren();

    assertEquals( 1, archiveEntries.length );
    ArchiveEntry archiveEntry = archiveEntries[ 0 ];
    assertTrue( archiveEntry instanceof FileEntry );
    assertEquals( "file.txt", archiveEntry.getName() );
  }

  @Test
  public void testRead_file_in_folder_zip() throws Exception {
    provisionZipFile( "file_in_folder.zip" );
    ArchiveReader archiveReader = createArchiveReader();

    Archive archive = archiveReader.read();
    ArchiveEntry[] archiveEntries = archive.getRootEntry().getChildren();

    assertEquals( 1, archiveEntries.length );
    ArchiveEntry archiveEntry = archiveEntries[ 0 ];
    assertTrue( archiveEntry instanceof DirectoryEntry );
    assertEquals( "folder", archiveEntry.getName() );
    DirectoryEntry folder = ( DirectoryEntry )archiveEntry;
    assertEquals( 1, folder.getChildren().length );
    assertTrue( folder.getChildren()[ 0 ] instanceof FileEntry );
    ArchiveEntry file = folder.getChildren()[ 0 ];
    assertEquals( "file.txt", file.getName() );
  }

  @Before
  public void setUp() {
    file = null;
  }

  @After
  public void tearDown() {
    if( file != null ) {
      FileHelper.delete( file );
    }
  }

  private void provisionZipFile( String resourceName ) throws IOException {
    file = new File ( System.getProperty( "java.io.tmpdir" ), "test.zip" );
    InputStream inputStream = getClass().getResourceAsStream( resourceName );
    FileHelper.copy( inputStream, file );
    inputStream.close();
  }

  private ArchiveReader createArchiveReader() {
    return new ArchiveReader( ARCHIVE_NAME, new Path( file.getAbsolutePath() ) );
  }
}
