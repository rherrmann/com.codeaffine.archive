package com.codeaffine.archive.contenttype;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.runtime.content.IContentType;
import org.junit.Test;


public class ContentTypesTest {

  @Test
  public void testIsZipContentTypeWithNullArgument() {
    boolean zipContentType = ContentTypes.isZipContentType( null );
    
    assertFalse( zipContentType );
  }
  
  @Test
  public void testIsZipContentTypeWithInvalidContentType() throws Exception {
    IContentType contentType = createContentType( null );
    
    boolean zipContentType = ContentTypes.isZipContentType( contentType );
    
    assertFalse( zipContentType );
  }

  @Test
  public void testIsZipContentTypeWithZipContentType() throws Exception {
    String id = "com.codeaffine.archive.contenttype.ZipContentType";
    IContentType contentType = createContentType( id );
    
    boolean zipContentType = ContentTypes.isZipContentType( contentType );
    
    assertTrue( zipContentType );
  }

  @Test
  public void testIsZipContentTypeWithFooContentType() throws Exception {
    IContentType contentType = createContentType( "foo" );
    
    boolean zipContentType = ContentTypes.isZipContentType( contentType );
    
    assertFalse( zipContentType );
  }
  
  private static IContentType createContentType( final String id ) {
    IContentType result = mock( IContentType.class );
    when( result.getId() ).thenReturn( id );
    return result;
  }
}
