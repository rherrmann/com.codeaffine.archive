package com.codeaffine.archive.ui.internal.viewer;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.codeaffine.archive.contenttype.ContentTypes;
import com.codeaffine.archive.ui.internal.model.ArchiveReader;
import com.codeaffine.archive.ui.internal.model.DirectoryEntry;
import com.codeaffine.archive.ui.internal.util.ContentTypeUtil;
import com.codeaffine.archive.ui.internal.util.StatusUtil;


class ArchiveContentProvider implements ITreeContentProvider {

  public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
  }

  public Object[] getElements( Object inputElement ) {
    return null;
  }

  public Object getParent( Object element ) {
    return null;
  }

  public Object[] getChildren( Object parentElement ) {
    Object[] result = null;
    if( parentElement instanceof IFile ) {
      result = getChildren( ( IFile )parentElement );
    } else if( parentElement instanceof DirectoryEntry ) {
      result = getChildren( ( DirectoryEntry )parentElement );
    }
    return result;
  }

  public boolean hasChildren( Object element ) {
    return isArchiveFile( element ) || isDirectoryEntry( element );
  }

  public void dispose() {
  }

  private static boolean isArchiveFile( Object element ) {
    boolean result = false;
    if( element instanceof IFile ) {
      IFile file = ( IFile )element;
      IContentDescription contentDescription = ContentTypeUtil.getContentDescription( file );
      result = ContentTypes.isZipContentType( contentDescription.getContentType() );
    }
    return result;
  }

  private static boolean isDirectoryEntry( Object element ) {
    boolean result = false;
    if( element instanceof DirectoryEntry ) {
      DirectoryEntry directoryEntry = ( DirectoryEntry )element;
      result = directoryEntry.hasChildren();
    }
    return result;
  }

  private static Object[] getChildren( IFile file ) {
    Object[] result = null;
    IPath location = file.getLocation();
    if( location != null ) {
      try {
        result = ArchiveReader.read( file );
      } catch( IOException ioe ) {
        StatusUtil.logError( ioe );
      }
    }
    return result;
  }

  private static Object[] getChildren( DirectoryEntry directoryEntry ) {
    return directoryEntry.getChildren();
  }
}
