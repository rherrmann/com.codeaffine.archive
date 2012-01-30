package com.codeaffine.archive.contenttype.internal;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.junit.Before;
import org.junit.Test;

import com.codeaffine.archive.contenttype.ContentTypes;


public class ExtensionTest {

  private IContentTypeManager contentTypeManager;

  @Test
  public void testZipFile() throws Exception {
    String fileName = "file.zip";

    IContentDescription contentDescription = getContentDescription( fileName );

    ContentTypes.isZipContentType( contentDescription.getContentType() );
  }

  @Test
  public void testWarFile() throws Exception {
    String fileName = "file.war";

    IContentDescription contentDescription = getContentDescription( fileName );

    ContentTypes.isZipContentType( contentDescription.getContentType() );
  }

  @Test
  public void testJarFile() throws Exception {
    String fileName = "file.jar";

    IContentDescription contentDescription = getContentDescription( fileName );

    ContentTypes.isZipContentType( contentDescription.getContentType() );
  }

  @Test
  public void testEarFile() throws Exception {
    String fileName = "file.ear";

    IContentDescription contentDescription = getContentDescription( fileName );

    ContentTypes.isZipContentType( contentDescription.getContentType() );
  }

  @Before
  public void setUp() {
    contentTypeManager = Platform.getContentTypeManager();
  }

  private IContentDescription getContentDescription( String fileName )
    throws IOException
  {
    InputStream inputStream = getClass().getResourceAsStream( fileName );
    QualifiedName[] all = IContentDescription.ALL;
    IContentDescription result = contentTypeManager.getDescriptionFor( inputStream, fileName, all );
    inputStream.close();
    return result;
  }
}
