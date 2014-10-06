package com.codeaffine.archive.contenttype;

import org.eclipse.core.runtime.content.IContentType;


public final class ContentTypes {
  private static final String ZIP_CONTENT_TYPE_ID
    = "com.codeaffine.archive.contenttype.ZipContentType";

  public static boolean isZipContentType( IContentType contentType ) {
    return contentType != null && ZIP_CONTENT_TYPE_ID.equals( contentType.getId() );
  }
  
  private ContentTypes() {
    // prevent instantiation
  }
}
