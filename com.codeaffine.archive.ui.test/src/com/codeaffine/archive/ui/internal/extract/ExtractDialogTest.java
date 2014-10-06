package com.codeaffine.archive.ui.internal.extract;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ExtractDialogTest {

  private Shell shell;
  private TestExtractDialog dialog;

  @Test
  public void testGetLocationAfterDialogWasClosed() throws Exception {
    dialog.open();
    dialog.close();

    ExtractLocation location = dialog.getLocation();

    assertNotNull( location );
    assertTrue( location.getPath().isEmpty() );
  }

  @Test
  public void testOKButtonIsInitiallyDisabled() throws Exception {
    dialog.open();
    Button okButton = dialog.getButton( IDialogConstants.OK_ID );

    assertFalse( okButton.getEnabled() );
  }

  @Before
  public void setUp() {
    shell = new Shell();
    dialog = new TestExtractDialog( shell );
    dialog.setBlockOnOpen( false );
  }

  @After
  public void tearDown() {
    dialog.close();
    shell.dispose();
  }

  private static class TestExtractDialog extends ExtractDialog {

    TestExtractDialog( Shell parent ) {
      super( parent );
    }

    @Override
    public Button getButton( int id ) {
      return super.getButton( id );
    }

  }
}
