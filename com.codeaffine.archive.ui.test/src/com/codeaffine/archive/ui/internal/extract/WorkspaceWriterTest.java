package com.codeaffine.archive.ui.internal.extract;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.codeaffine.archive.testfixture.TestProject;


public class WorkspaceWriterTest {
  private static final byte[] CONTENT = new byte[] { 1, 2, 3 };

  private TestProject project;

  @Test
  public void testWriteFile() throws Exception {
    IPath destinationFileName = new Path( "file.txt" );
    ExtractWriter writer = new WorkspaceWriter( project.getProject().getFullPath() );

    writer.write( destinationFileName, new ByteArrayInputStream( CONTENT ) );

    IFile file = project.getProject().getFile( destinationFileName );
    assertTrue( file.exists() );
    byte[] writtenContent = read( file );
    assertEquals( CONTENT, writtenContent );
  }

  @Test
  public void testWriteFileInFolder() throws Exception {
    IPath destinationFileName = new Path( "folder/file.txt" );
    ExtractWriter writer = new WorkspaceWriter( project.getProject().getFullPath() );

    writer.write( destinationFileName, new ByteArrayInputStream( CONTENT ) );

    IFile file = project.getProject().getFile( destinationFileName );
    assertTrue( file.exists() );
    byte[] writtenContent = read( file );
    assertEquals( CONTENT, writtenContent );
  }

  @Test
  public void testWriteFileInSubFolder() throws Exception {
    IPath destinationFileName = new Path( "foo/bar/file.txt" );
    ExtractWriter writer = new WorkspaceWriter( project.getProject().getFullPath() );

    writer.write( destinationFileName, new ByteArrayInputStream( CONTENT ) );

    IFile file = project.getProject().getFile( destinationFileName );
    assertTrue( file.exists() );
    byte[] writtenContent = read( file );
    assertEquals( CONTENT, writtenContent );
  }

  @Test
  public void testWriteFileToExistingFolder() throws Exception {
    project.createFolder( "folder" );
    IPath destinationFileName = new Path( "folder/file.txt" );
    ExtractWriter writer = new WorkspaceWriter( project.getProject().getFullPath() );

    writer.write( destinationFileName, new ByteArrayInputStream( CONTENT ) );

    IFile file = project.getProject().getFile( destinationFileName );
    assertTrue( file.exists() );
  }

  @Test
  public void testWriteFileToExistingFile() throws Exception {
    String destinationFileName = "file.txt";
    TestProject.createFile( project.getProject(), destinationFileName, "" );
    ExtractWriter writer = new WorkspaceWriter( project.getProject().getFullPath() );

    writer.write( new Path( destinationFileName ), new ByteArrayInputStream( CONTENT ) );

    IFile file = project.getProject().getFile( destinationFileName );
    assertTrue( file.exists() );
  }

  @Before
  public void setUp() {
    project = new TestProject();
  }

  @After
  public void tearDown() throws Exception {
    TestProject.deleteAll();
  }

  private static void assertEquals( byte[] expected, byte[] actual ) {
    Arrays.equals( actual, expected );
  }

  private static byte[] read( IFile file ) throws Exception {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    InputStream inputStream = file.getContents();
    try {
      int read = inputStream.read();
      while( read != -1 ) {
        read = inputStream.read();
        outputStream.write( read );
      }
    } finally {
      inputStream.close();
    }
    return outputStream.toByteArray();
  }
}
