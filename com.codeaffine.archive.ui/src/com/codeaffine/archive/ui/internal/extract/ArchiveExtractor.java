package com.codeaffine.archive.ui.internal.extract;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.codeaffine.archive.ui.internal.model.ArchiveEntry;
import com.codeaffine.archive.ui.internal.model.DirectoryEntry;
import com.codeaffine.archive.ui.internal.model.FileEntry;
import com.codeaffine.archive.ui.internal.util.StatusUtil;


class ArchiveExtractor {
  private final ArchiveEntry archiveEntry;
  private final IProgressMonitor progressMonitor;
  private ExtractWriter extractWriter;

  ArchiveExtractor( ArchiveEntry archiveEntry, IProgressMonitor progressMonitor ) {
    this.archiveEntry = archiveEntry;
    this.progressMonitor = progressMonitor;
  }

  void extract( ExtractWriter extractWriter ) throws CoreException {
    this.extractWriter = extractWriter;
    extract();
  }

  private void extract() throws CoreException {
    if( archiveEntry instanceof FileEntry ) {
      extractFile( ( FileEntry )archiveEntry );
    } else if( archiveEntry instanceof DirectoryEntry ) {
      extractDirectory( ( DirectoryEntry )archiveEntry );
    }
  }

  private void extractDirectory( DirectoryEntry directoryEntry ) throws CoreException {
    checkProgressMonitor();
    ArchiveEntry[] children = directoryEntry.getChildren();
    for( ArchiveEntry child : children ) {
      if( child instanceof FileEntry ) {
        extractFile( ( FileEntry )child );
      } else if( child instanceof DirectoryEntry ) {
        extractDirectory( ( DirectoryEntry )child );
      }
    }
  }

  private void extractFile( FileEntry fileEntry ) throws CoreException {
    checkProgressMonitor();
    IProgressMonitor subProgressMonitor = beginSubProgressMonitor( fileEntry );
    try {
      writeFile( fileEntry );
    } finally {
      subProgressMonitor.done();
    }
  }

  private void writeFile( FileEntry fileEntry ) throws CoreException {
    try {
      InputStream inputStream = new BufferedInputStream( fileEntry.open() );
      try {
        extractWriter.write( getPath( fileEntry ), inputStream );
      } finally {
        inputStream.close();
      }
    } catch( IOException ioe ) {
      throw new CoreException( StatusUtil.createErrorStatus( ioe ) );
    }
  }

  private IProgressMonitor beginSubProgressMonitor( FileEntry fileEntry ) {
    IProgressMonitor result = new SubProgressMonitor( progressMonitor, 1 );
    String taskName = MessageFormat.format( "Extracting {0}...", fileEntry.getName() );
    result.beginTask( taskName, IProgressMonitor.UNKNOWN );
    result.subTask( taskName );
    return result;
  }

  private IPath getPath( FileEntry fileEntry ) {
    String result = "";
    ArchiveEntry entry = fileEntry;
    while( entry != null ) {
      if( result.length() > 0 ) {
        result = "/" + result;
      }
      result = entry.getName() + result;
      entry = entry.getParent();
      if( entry == archiveEntry ) {
        result = entry.getName() + "/" + result;
        entry = null;
      }
    }
    return new Path( result );
  }

  private void checkProgressMonitor() {
    if( progressMonitor.isCanceled() ) {
      throw new OperationCanceledException();
    }
  }
}
