package com.codeaffine.archive.ui.internal.extract;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.codeaffine.archive.ui.internal.model.ArchiveEntry;
import com.codeaffine.archive.ui.internal.model.ArchiveReader;
import com.codeaffine.archive.ui.internal.util.StatusUtil;


public class ExtractFileAction implements IObjectActionDelegate {

  private IWorkbenchPart part;
  private IFile file;

  public void setActivePart( IAction action, IWorkbenchPart targetPart ) {
    this.part = targetPart;
  }

  public void selectionChanged( IAction action, ISelection selection ) {
    updateSelection( selection );
    updateEnablement( action );
  }

  public void run( IAction action ) {
    if( file != null ) {
      ExtractLocation extractLocation = ExtractDialog.chooseLocation( part.getSite().getShell() );
      if( extractLocation != null ) {
        extract( extractLocation );
      }
    }
  }

  private void extract( ExtractLocation extractLocation ) {
    try {
      runExtractJob( extractLocation );
    } catch( IOException ioe ) {
      StatusUtil.show( ioe );
    }
  }

  private void runExtractJob( ExtractLocation extractLocation ) throws IOException {
    ArchiveEntry[] archiveEntries = ArchiveReader.read( file );
    ArchiveExtractorJob job = new ArchiveExtractorJob( archiveEntries, extractLocation );
    job.setUser( true );
    job.schedule();
  }

  private void updateSelection( ISelection selection ) {
    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
      file = ( IFile )structuredSelection.getFirstElement();
    } else {
      file = null;
    }
  }

  private void updateEnablement( IAction action ) {
    action.setEnabled( file != null );
  }
}
