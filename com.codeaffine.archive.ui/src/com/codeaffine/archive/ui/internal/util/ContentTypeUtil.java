package com.codeaffine.archive.ui.internal.util;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;

import com.codeaffine.archive.ui.internal.model.FileEntry;


public class ContentTypeUtil {

  public static IContentDescription getContentDescription( IFile file ) {
    IContentDescription result = null;
    try {
      result = file.getContentDescription();
    } catch( CoreException ignore ) {
    }
    if( result == null ) {
      result = new NullContentDescription();
    }
    return result;
  }

  public static IContentDescription getContentDescription( FileEntry fileEntry ) throws IOException
  {
    InputStream inputStream = fileEntry.open();
    try {
      return getContentDescription( fileEntry.getName(), inputStream );
    } finally {
      inputStream.close();
    }
  }

  public static IContentType getContentType( String fileName ) {
    IContentTypeManager contentTypeManager = Platform.getContentTypeManager();
    return contentTypeManager.findContentTypeFor( fileName );
  }

  private static IContentDescription getContentDescription( String name, InputStream content )
    throws IOException
  {
    IContentTypeManager contentTypeManager = Platform.getContentTypeManager();
    QualifiedName[] all = IContentDescription.ALL;
    IContentDescription result = contentTypeManager.getDescriptionFor( content, name, all );
    if( result == null ) {
      result = new NullContentDescription();
    }
    return result;
  }

  private ContentTypeUtil() {
    // prevent instantiation
  }

  static class NullContentDescription implements IContentDescription {

    public boolean isRequested( QualifiedName key ) {
      return false;
    }

    public String getCharset() {
      return null;
    }

    public IContentType getContentType() {
      return null;
    }

    public Object getProperty( QualifiedName key ) {
      return null;
    }

    public void setProperty( QualifiedName key, Object value ) {
    }
  }
}
