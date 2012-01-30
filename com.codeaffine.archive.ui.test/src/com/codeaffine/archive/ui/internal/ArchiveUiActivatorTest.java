package com.codeaffine.archive.ui.internal;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.codeaffine.archive.ui.internal.ArchiveUiActivator;


public class ArchiveUiActivatorTest {
  
  @Test
  public void testGetDefault() {
    assertNotNull( ArchiveUiActivator.getDefault() );
  }
}
