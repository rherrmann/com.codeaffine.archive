package com.codeaffine.archive.ui.internal.util;

import static org.junit.Assert.*;

import org.eclipse.core.runtime.IStatus;
import org.junit.Test;

import com.codeaffine.archive.ui.internal.util.StatusUtil;


public class StatusUtilTest {

  @Test
  public void testCreateStatus() {
    String message = "message";
    Throwable exception = new Throwable( message );
    
    IStatus status = StatusUtil.createErrorStatus( exception );
    
    assertEquals( message, status.getMessage() );
    assertSame( exception, status.getException() );
    assertEquals( "com.codeaffine.archive.ui", status.getPlugin() );
  }

  @Test
  public void testCreateStatusWithMessage() {
    String message = "message";
    Throwable exception = new Throwable();
    
    IStatus status = StatusUtil.createStatus( IStatus.ERROR, message, exception );
    
    assertEquals( message, status.getMessage() );
    assertSame( exception, status.getException() );
    assertEquals( "com.codeaffine.archive.ui", status.getPlugin() );
  }
}
