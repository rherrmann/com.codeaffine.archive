package com.codeaffine.archive.ui.internal.extract;

import com.codeaffine.archive.ui.internal.model.ArchiveEntry;
import com.codeaffine.archive.ui.internal.model.DirectoryEntry;
import com.codeaffine.archive.ui.internal.model.FileEntry;


class FileEntryCounter {
  private final ArchiveEntry[] archiveEntries;
  private int count;

  FileEntryCounter( ArchiveEntry... archiveEntries ) {
    this.archiveEntries = archiveEntries;
  }

  int count() {
    count = 0;
    countFileEntries( archiveEntries );
    return count;
  }

  private void countFileEntries( ArchiveEntry[] archiveEntries ) {
    for( ArchiveEntry archiveEntry : archiveEntries ) {
      if( archiveEntry instanceof FileEntry ) {
        count++;
      } else if( archiveEntry instanceof DirectoryEntry ) {
        DirectoryEntry directoryEntry = ( DirectoryEntry )archiveEntry;
        countFileEntries( directoryEntry.getChildren() );
      }
    }
  }
}
