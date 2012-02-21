package com.codeaffine.archive.ui.internal.extract;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Arrays;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.codeaffine.archive.testfixture.FileHelper;


public class FilesystemWriterTest {
  private static final byte[] CONTENT = new byte[] { 1, 2, 3 };

  private File directory;

  @Test
  public void testWriteFile() throws Exception {
    IPath destinationFileName = new Path( "file.txt" );
    FilesystemWriter writer = new FilesystemWriter( directory );

    writer.write( destinationFileName, new ByteArrayInputStream( CONTENT ) );

    File destinationFile = new File( directory, destinationFileName.toPortableString() );
    assertTrue( destinationFile.exists() );
    assertTrue( destinationFile.isFile() );
    byte[] writenContent = FileHelper.read( destinationFile );
    assertEquals( CONTENT, writenContent );
  }

  @Test
  public void testWriteFileInFolder() throws Exception {
    IPath destinationFileName = new Path( "folder/file.txt" );
    FilesystemWriter writer = new FilesystemWriter( directory );

    writer.write( destinationFileName, new ByteArrayInputStream( new byte[ 0 ] ) );

    File destinationFile = new File( directory, destinationFileName.toPortableString() );
    assertTrue( destinationFile.exists() );
    assertTrue( destinationFile.isFile() );
    byte[] writenContent = FileHelper.read( destinationFile );
    assertEquals( CONTENT, writenContent );
  }

  @Before
  public void setUp() {
    directory = FileHelper.getTempDirectory( getClass().getSimpleName() );
  }

  @After
  public void tearDown() {
    FileHelper.delete( directory );
  }

  private static void assertEquals( byte[] expected, byte[] actual ) {
    Arrays.equals( actual, expected );
  }
}
