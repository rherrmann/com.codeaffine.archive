package com.codeaffine.archive.ui.internal.extract;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.codeaffine.archive.ui.internal.util.StatusUtil;

class ExtractDialog extends StatusDialog {
  private final IStatus initialStatus;
  private Composite parent;
  private Text txtLocation;
  private ExtractLocation location;

  static ExtractLocation chooseLocation( Shell shell ) {
    ExtractLocation result = null;
    ExtractDialog dialog = new ExtractDialog( shell );
    if( dialog.open() == Window.OK ) {
      result = dialog.getLocation();
    }
    return result;
  }

  ExtractDialog( Shell parent ) {
    super( parent );
    setTitle( "Extract to" );
    location = new ExtractLocation( "" );
    initialStatus = createInitialStatus();
    updateStatus( initialStatus );
  }

  ExtractLocation getLocation() {
    return location;
  }

  @Override
  protected boolean isResizable() {
    return true;
  }

  @Override
  protected Control createDialogArea( Composite parent ) {
    this.parent = ( Composite )super.createDialogArea( parent );
    createDialogContents();
    applyDialogFont( parent );
    return parent;
  }

  @Override
  protected void okPressed() {
    updateLocation();
    super.okPressed();
  }

  @Override
  protected void updateButtonsEnableState( IStatus status ) {
    super.updateButtonsEnableState( status );
    if( getStatus() == initialStatus ) {
      Button button = getButton( IDialogConstants.OK_ID );
      if( button != null && !button.isDisposed() ) {
        button.setEnabled( false );
      }
    }
  }

  private void createDialogContents() {
    parent.setLayout( GridLayoutFactory.swtDefaults().numColumns( 3 ).margins( 10, 10 ).create() );
    createDescriptionLabel();
    createFiller();
    createLocationLabel();
    createLocationText();
    createExternalFolderButton();
    createFiller();
    createFiller();
    createWorkspaceButton();
    txtLocation.setFocus();
  }

  private void createDescriptionLabel() {
    String text = "Enter the folder to which the selected items will be extracted";
    GridData gridData = new GridData( SWT.LEFT, SWT.TOP, true, false, 2, 1 );
    createLabel( text, gridData );
  }

  private void createLocationLabel() {
    GridData gridData = new GridData( SWT.LEFT, SWT.CENTER, false, false );
    createLabel( "&Location", gridData );
  }

  private void createLocationText() {
    txtLocation = new Text( parent, SWT.BORDER );
    txtLocation.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    txtLocation.addModifyListener( event -> updateLocation() );
  }

  private void createExternalFolderButton() {
    Button btnExternalFolder = createButton( "&External Folder..." );
    btnExternalFolder.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent event ) {
        browseExternalFolder();
      }
    } );
  }

  private void createWorkspaceButton() {
    Button btnWorkspace = createButton( "&Workspace..." );
    btnWorkspace.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent event ) {
        browseWorkspaceFolder();
      }
    } );
  }

  private Label createLabel( String text, GridData layoutData ) {
    Label result = new Label( parent, SWT.NONE );
    result.setText( text );
    result.setLayoutData( layoutData );
    return result;
  }

  private Button createButton( String text ) {
    Button result = new Button( parent, SWT.PUSH );
    result.setText( text );
    result.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false ) );
    return result;
  }

  @SuppressWarnings("unused")
  private void createFiller() {
    new Label( parent, SWT.NONE );
  }

  private void browseExternalFolder() {
    IPath currentLocation = getLocation().getPath();
    DirectoryDialog dialog = new DirectoryDialog( getShell() );
    dialog.setMessage( "Choose a folder to which the selected items will be extracted" );
    dialog.setText( "Folder Selection" );
    dialog.setFilterPath( currentLocation.toOSString() );
    String newLocation = dialog.open();
    if( newLocation != null ) {
      txtLocation.setText( newLocation );
    }
  }

  private void browseWorkspaceFolder() {
    WorkspaceFolderSelectionDialog dialog = new WorkspaceFolderSelectionDialog( getShell() );
    dialog.setInitialSelection( location.getPath() );
    if( dialog.open() == Window.OK ) {
      txtLocation.setText( dialog.getSelection().toPortableString() );
    }
  }

  private void updateLocation() {
    location = new ExtractLocation( txtLocation.getText().trim() );
    updateStatus( location.validate() );
  }

  private static IStatus createInitialStatus() {
    return StatusUtil.createStatus( IStatus.OK, "", null );
  }
}
