package com.codeaffine.archive.testfixture;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public final class TestProject {
  private static final String PLUGIN_ID = "com.codeaffine.archive.ui.test";

  private static int uniqueId = 0;
  private static List<TestProject> projects = new LinkedList<TestProject>();

  public static void deleteAll() throws CoreException {
    while( projects.size() > 0 ) {
      TestProject project = projects.get( 0 );
      project.delete();
    }
  }

  private final String projectName;
  private IProject project;

  public TestProject() {
    projectName = "test.project." + uniqueId;
    uniqueId++;
  }

  public String getName() {
    initializeProject();
    return project.getName();
  }

  public IProject getProject() {
    initializeProject();
    return project;
  }

  public IFolder createFolder( String name ) throws CoreException {
    initializeProject();
    IFolder result = project.getFolder( name );
    if( !result.exists() ) {
      result.create( true, true, newProgressMonitor() );
    }
    return result;
  }

  public static IFile createFile( IContainer parent, String fileName, String content )
    throws CoreException
  {
    InputStream stream = toUtf8Stream( content );
    return createFile( parent, fileName, stream );
  }

  public static IFile createFile( IContainer parent, String fileName, InputStream stream )
    throws CoreException
  {
    IFile result = parent.getFile( new Path( fileName ) );
    if( !result.exists() ) {
      result.create( stream, true, newProgressMonitor() );
    } else {
      result.setContents( stream, false, false, newProgressMonitor() );
    }
    return result;
  }

  public void delete() throws CoreException {
    if( isProjectCreated() ) {
      projects.remove( this );
      project.delete( true, true, newProgressMonitor() );
    }
  }

  //////////////////////////
  // Project creation helper

  private boolean isProjectCreated() {
    return project != null;
  }

  private void initializeProject() {
    if( !isProjectCreated() ) {
      try {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        project = root.getProject( projectName );
        project.create( newProgressMonitor() );
        project.open( newProgressMonitor() );
        waitForAutoBuild();
      } catch( CoreException ce ) {
        throw new RuntimeException( ce );
      }
      projects.add( this );
    }
  }

  private static void waitForAutoBuild() throws CoreException {
    try {
      Job.getJobManager().join( ResourcesPlugin.FAMILY_AUTO_BUILD, newProgressMonitor() );
    } catch( OperationCanceledException e ) {
      handleException( "waitForAutoBuild failed", e );
    } catch( InterruptedException e ) {
      handleException( "waitForAutoBuild failed", e );
    }
  }

  private static IProgressMonitor newProgressMonitor() {
    return new NullProgressMonitor();
  }

  private static InputStream toUtf8Stream( final String string ) {
    try {
      return new ByteArrayInputStream( string.getBytes( "UTF-8" ) );
    } catch( UnsupportedEncodingException uee ) {
      throw new RuntimeException( "Failed to encode string to UTF-8.", uee );
    }
  }

  private static void handleException( String msg, Throwable throwable ) throws CoreException {
    IStatus status = new Status( IStatus.ERROR, PLUGIN_ID, msg, throwable );
    throw new CoreException( status );
  }
}
