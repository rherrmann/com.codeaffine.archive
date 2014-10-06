package com.codeaffine.archive.ui.internal.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.content.IContentDescription;
import org.junit.Test;

import com.codeaffine.archive.ui.internal.model.FileEntry;
import com.codeaffine.archive.ui.internal.util.ContentTypeUtil.NullContentDescription;


public class ContentTypeUtilTest {

  @Test
  public void testGetContentDescriptionForFile() throws Exception {
    IContentDescription contentDescription = mock( IContentDescription.class );
    IFile file = mock( IFile.class );
    when( file.getContentDescription() ).thenReturn( contentDescription );

    IContentDescription returnedCD = ContentTypeUtil.getContentDescription( file );

    assertSame( contentDescription, returnedCD );
  }

  @Test
  public void testGetContentDescriptionForFileWithException() throws Exception {
    IFile file = mock( IFile.class );
    CoreException coreException = mock( CoreException.class );
    when( file.getContentDescription() ).thenThrow( coreException );

    IContentDescription contentDescription = ContentTypeUtil.getContentDescription( file );

    assertNotNull( contentDescription );
    assertEquals( NullContentDescription.class, contentDescription.getClass() );
  }

  @Test
  public void testGetContentDescriptionForFileWhenNull() throws Exception {
    IFile file = mock( IFile.class );
    when( file.getContentDescription() ).thenReturn( null );

    IContentDescription contentDescription = ContentTypeUtil.getContentDescription( file );

    assertNotNull( contentDescription );
    assertEquals( NullContentDescription.class, contentDescription.getClass() );
  }

  @Test
  public void testGetContentDescriptionForContent() throws Exception {
    FileEntry fileEntry = createFileEntry( "name.txt" );
    IContentDescription description = ContentTypeUtil.getContentDescription( fileEntry );

    assertNotNull( description );
  }

  @Test
  public void testGetContentDescriptionForContentWithoutName() throws Exception {
    FileEntry fileEntry = createFileEntry( "" );
    IContentDescription description = ContentTypeUtil.getContentDescription( fileEntry );

    assertNotNull( description );
    assertEquals( NullContentDescription.class, description.getClass() );
  }

  private static FileEntry createFileEntry( String name ) throws IOException {
    FileEntry result = mock( FileEntry.class );
    when( result.getName() ).thenReturn( name );
    when( result.open() ).thenReturn( new ByteArrayInputStream( new byte[ 0 ] ) );
    return result;
  }

}
