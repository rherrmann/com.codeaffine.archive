package com.codeaffine.archive.ui.internal.extract;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.NewFolderDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

import com.codeaffine.archive.ui.internal.ArchiveUiActivator;


class WorkspaceFolderSelectionDialog extends ElementTreeSelectionDialog {

  private final IWorkspaceRoot workspaceRoot;

  WorkspaceFolderSelectionDialog( Shell shell ) {
    super( shell, new WorkbenchLabelProvider(), new WorkbenchContentProvider() );
    workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    initialize();
  }

  private void initialize() {
    setAllowMultiple( false );
    setStatusLineAboveButtons( false );
    setValidator( new WorkspaceFolderValidator() );
    addFilter( new WorkspaceFolderFilter() );
    setComparator( new ResourceComparator( ResourceComparator.NAME ) );
    setTitle( "Folder Selection" );
    setMessage( "Choose a folder to which the selected items will be extracted" );
    setInput( workspaceRoot );
    setHelpAvailable( false );
  }

  void setInitialSelection( IPath path ) {
    setInitialSelection( workspaceRoot.findMember( path ) );
  }

  IPath getSelection() {
    IContainer selectedFolder = ( IContainer )getFirstResult();
    return selectedFolder.getFullPath();
  }

  @Override
  protected void createButtonsForButtonBar( Composite parent ) {
    Button button = createButton( parent, IDialogConstants.OPEN_ID, "&New Folder...", false );
    button.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent event ) {
        handleNewFolder();
      }
    } );
    super.createButtonsForButtonBar( parent );
  }

  private void handleNewFolder() {
    NewFolderDialog dialog = new NewFolderDialog( getShell(), ( IContainer )getFirstResult() );
    if( dialog.open() == Window.OK ) {
      selectElement( dialog.getFirstResult() );
    }
  }

  private void selectElement( Object element ) {
    ISelection selection = new StructuredSelection( element );
    getTreeViewer().setSelection( selection, true );
  }

  static class WorkspaceFolderFilter extends ViewerFilter {
    private static final Class<?>[] ACCEPTED_TYPES = new Class[]{ IProject.class, IFolder.class };

    @Override
    public boolean select( Viewer viewer, Object parentElement, Object element ) {
      boolean result = false;
      for( int i = 0; !result && i < ACCEPTED_TYPES.length; i++ ) {
        if( ACCEPTED_TYPES[ i ].isInstance( element ) ) {
          result = true;
        }
      }
      return result;
    }
  }

  static class WorkspaceFolderValidator implements ISelectionStatusValidator {
    private static final Class<?>[] ACCEPTED_TYPES = new Class[]{ IProject.class, IFolder.class };

    public IStatus validate( Object[] elements ) {
      String pluginId = ArchiveUiActivator.getDefault().getBundle().getSymbolicName();
      IStatus result;
      if( !isValid( elements ) ) {
        result = new Status( IStatus.ERROR, pluginId, "" );
      } else {
        result = new Status( IStatus.OK, pluginId, "" );
      }
      return result;
    }

    private static boolean isValid( Object[] selection ) {
      boolean result = false;
      if( selection.length == 1 && isOfAcceptedType( selection[ 0 ] ) ) {
        result = true;
      }
      return result;
    }

    private static boolean isOfAcceptedType( Object object ) {
      boolean result = false;
      for( int i = 0; !result && i < ACCEPTED_TYPES.length; i++ ) {
        if( ACCEPTED_TYPES[ i ].isInstance( object ) ) {
          result = true;
        }
      }
      return result;
    }
  }
}
