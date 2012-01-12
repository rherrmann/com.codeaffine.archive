package com.codeaffine.archive.ui.internal.viewer;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.codeaffine.archive.ui.internal.util.StatusUtil;


public class ArchiveContentProviderWrapper implements ITreeContentProvider {
  private ITreeContentProvider delegate;

  public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
    updateDelegate();
    logWarning();
  }

  public Object[] getElements( Object inputElement ) {
    return delegate.getElements( inputElement );
  }

  public Object[] getChildren( Object parentElement ) {
    return delegate.getChildren( parentElement );
  }

  public Object getParent( Object element ) {
    return delegate.getParent( element );
  }

  public boolean hasChildren( Object element ) {
    return delegate.hasChildren( element );
  }

  public void dispose() {
    delegate.dispose();
  }

  private void updateDelegate() {
    String fileSystemScheme = ResourcesPlugin.getWorkspace().getRoot().getLocationURI().getScheme();
    if( "file".equals( fileSystemScheme ) ) {
      delegate = new ArchiveContentProvider();
    } else {
      delegate = new NullContentProvider();
    }
  }

  private void logWarning() {
    if( delegate instanceof NullContentProvider ) {
      String msg
        = "The Zip Archive extension for the Project Explorer can only operate on "
        + "workspaces backed by the local filesystem.";
      StatusUtil.log( IStatus.WARNING, msg );
    }
  }

  private static class NullContentProvider implements ITreeContentProvider {
    public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
    }

    public Object[] getElements( Object inputElement ) {
      return null;
    }

    public Object[] getChildren( Object parentElement ) {
      return null;
    }

    public Object getParent( Object element ) {
      return null;
    }

    public boolean hasChildren( Object element ) {
      return false;
    }

    public void dispose() {
    }
  }
}
