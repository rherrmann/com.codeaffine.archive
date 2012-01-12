package com.codeaffine.archive.ui.internal.viewer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.codeaffine.archive.ui.internal.model.DirectoryEntry;
import com.codeaffine.archive.ui.internal.model.FileEntry;
import com.codeaffine.archive.ui.internal.viewer.ArchiveEntrySorter;


public class ArchiveEntrySorterTest {

  private ArchiveEntrySorter sorter;

  @Test
  public void testCompareDirectoryAVsDirectoryB() {
    int compare = sorter.compare( null, createDirectoryEntry( "A" ), createDirectoryEntry( "B" ) );

    assertEquals( -1, compare );
  }

  @Test
  public void testCompareDirectoryBVsDirectoryA() {
    int compare = sorter.compare( null, createDirectoryEntry( "B" ), createDirectoryEntry( "A" ) );

    assertEquals( 1, compare );
  }

  @Test
  public void testCompareFileBVsDirectoryA() {
    int compare = sorter.compare( null, createFileEntry( "A" ), createDirectoryEntry( "A" ) );

    assertEquals( 1, compare );
  }

  @Test
  public void testCompareDirectoryAVsFileB() {
    int compare = sorter.compare( null, createDirectoryEntry( "A" ), createFileEntry( "B" ) );

    assertEquals( -1, compare );
  }

  @Test
  public void testCompareFileAVsFileB() {
    int compare = sorter.compare( null, createFileEntry( "A" ), createFileEntry( "B" ) );

    assertEquals( -1, compare );
  }

  @Test
  public void testCompareFileBVsFileA() {
    int compare = sorter.compare( null, createFileEntry( "B" ), createFileEntry( "A" ) );

    assertEquals( 1, compare );
  }

  private static FileEntry createFileEntry( String name ) {
    FileEntry result = mock( FileEntry.class );
    when( result.getName() ).thenReturn( name );
    return result;
  }

  private static DirectoryEntry createDirectoryEntry( String name ) {
    DirectoryEntry result = mock( DirectoryEntry.class );
    when( result.getName() ).thenReturn( name );
    return result;
  }

  @Before
  public void setUp() {
    sorter = new ArchiveEntrySorter();
  }
}
