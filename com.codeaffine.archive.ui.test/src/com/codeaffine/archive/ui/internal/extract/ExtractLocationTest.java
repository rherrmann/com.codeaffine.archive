package com.codeaffine.archive.ui.internal.extract;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.codeaffine.archive.testfixture.FileHelper;
import com.codeaffine.archive.testfixture.TestProject;


public class ExtractLocationTest {

  private File directory;
  private TestProject project;

  @Test
  public void testGetPath() {
    ExtractLocation extractLocation = new ExtractLocation( "path/to/folder" );

    IPath path = extractLocation.getPath();

    assertEquals( "path/to/folder", path.toPortableString() );
  }

  @Test
  public void testIsWorkspaceRelativeWithExistingWorkspaceFolder() throws Exception {
    IFolder folder = project.createFolder( "foo" );
    String path = folder.getFullPath().toPortableString();
    ExtractLocation extractLocation = new ExtractLocation( path );

    boolean workspaceRelative = extractLocation.isWorkspaceRelative();

    assertTrue( workspaceRelative );
  }

  @Test
  public void testIsWorkspaceRelativeWithNonExistingFolder() throws Exception {
    ExtractLocation extractLocation = new ExtractLocation( "foo" );

    boolean workspaceRelative = extractLocation.isWorkspaceRelative();

    assertFalse( workspaceRelative );
  }

  @Test
  public void testValidateWithEmptyPath() throws Exception {
    ExtractLocation extractLocation = new ExtractLocation( "" );

    IStatus status = extractLocation.validate();

    assertEquals( IStatus.ERROR, status.getSeverity() );
  }

  @Test
  public void testValidateWithExistingFilesystemFolder() throws Exception {
    directory.mkdirs();
    ExtractLocation extractLocation = new ExtractLocation( directory.getCanonicalPath() );

    IStatus status = extractLocation.validate();

    assertTrue( status.isOK() );
  }

  @Test
  public void testValidateWithExistingWorkspaceFolder() throws Exception {
    IFolder folder = project.createFolder( "foo" );
    String path = folder.getFullPath().toPortableString();
    ExtractLocation extractLocation = new ExtractLocation( path );

    IStatus status = extractLocation.validate();

    assertTrue( status.isOK() );
  }

  @Test
  public void testValidateWithNonExistingFolder() throws Exception {
    ExtractLocation extractLocation = new ExtractLocation( "/path/to/workspace/folder" );

    IStatus status = extractLocation.validate();

    assertEquals( IStatus.ERROR, status.getSeverity() );
  }

  @Test
  public void testValidateWithNonEmptyFilesystemFolder() throws Exception {
    directory.mkdirs();
    File file = new File( directory, "file.txt" );
    file.createNewFile();
    ExtractLocation extractLocation = new ExtractLocation( directory.getCanonicalPath() );

    IStatus status = extractLocation.validate();

    assertEquals( IStatus.WARNING, status.getSeverity() );
  }

  @Test
  public void testValidateWithNonEmptyWorkspaceFolder() throws Exception {
    IFolder folder = project.createFolder( "foo" );
    TestProject.createFile( folder, "file.txt", "" );
    String path = folder.getFullPath().toPortableString();
    ExtractLocation extractLocation = new ExtractLocation( path );

    IStatus status = extractLocation.validate();

    assertEquals( IStatus.WARNING, status.getSeverity() );
  }

  @Before
  public void setUp() {
    directory = FileHelper.getTempDirectory( getClass().getSimpleName() );
    project = new TestProject();
  }

  @After
  public void tearDown() throws Exception {
    FileHelper.delete( directory );
    TestProject.deleteAll();
  }
}
