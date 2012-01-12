package com.codeaffine.archive.ui.internal.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.codeaffine.archive.ui.test.FileHelper;


public class FileEntryTest {
  private Archive archive;
  private File archiveLocation;

  @Test
  public void testOpen() throws Exception {
    provisionFile( "file.zip" );
    ZipEntry zipEntry = getZipFileEntry();
    DirectoryEntry directoryEntry = new DirectoryEntry( archive, "" );
    FileEntry archiveEntry = new FileEntry( directoryEntry, zipEntry );

    InputStream inputStream = archiveEntry.open();
    inputStream.close();

    assertNotNull( inputStream );
  }

  @Test
  public void testGetArchive() throws Exception {
    DirectoryEntry directoryEntry = new DirectoryEntry( archive, "" );
    FileEntry fileEntry = new FileEntry( directoryEntry, mock( ZipEntry.class ) );

    Archive returnedArchive = fileEntry.getArchive();

    assertSame( archive, returnedArchive );
  }

  @Before
  public void setUp() throws IOException {
    archiveLocation = new File( System.getProperty( "java.io.tmpdir" ), "test.zip" );
    archive = new Archive( "archive-name", new Path( archiveLocation.getCanonicalPath() ) );
  }

  @After
  public void tearDown() {
    FileHelper.delete( archiveLocation );
  }

  private void provisionFile( String name ) throws IOException {
    InputStream inputStream = getClass().getResourceAsStream( name );
    FileHelper.copy( inputStream, archiveLocation );
    inputStream.close();
  }

  private ZipEntry getZipFileEntry() throws IOException {
    ZipFile zipFile = new ZipFile( archiveLocation );
    Enumeration<? extends ZipEntry> entries = zipFile.entries();
    ZipEntry result = entries.nextElement();  // file.txt
    zipFile.close();
    return result;
  }
}
