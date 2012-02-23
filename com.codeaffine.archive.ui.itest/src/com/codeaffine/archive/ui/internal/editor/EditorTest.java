package com.codeaffine.archive.ui.internal.editor;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.codeaffine.archive.testfixture.TestProject;


@RunWith(SWTBotJunit4ClassRunner.class)
public class EditorTest {
  private static final String ARCHIVE_ZIP = "archive.zip";

  private TestProject testProject;
  private SWTWorkbenchBot workbenchBot;
  private ProjectExplorerBot projectExplorerBot;


  @Test
  public void testOpenArchiveEntryByDoubleClick() {
    projectExplorerBot.showView();
    SWTBotTree tree = projectExplorerBot.getTree();
    SWTBotTreeItem fileItem = selectArchiveEntry( tree );
    fileItem.doubleClick();

    assertEquals( TestArchiveFile.ENTRY_FILE_NAME, workbenchBot.activeEditor().getTitle() );
  }

// TODO [rh] Disabled until bug 372170 (pressShortcut( Keystrokes.CR ) fails with "Invalid key code")
//      is resolved
//  @Test
//  public void testOpenArchiveEntryByReturnKey() {
//    projectExplorerBot.showView();
//    SWTBotTree tree = projectExplorerBot.getTree();
//    SWTBotTreeItem fileItem = selectArchiveEntry( tree );
//    fileItem.pressShortcut( Keystrokes.CR );
//
//    assertEquals( FILE_TXT, bot.activeEditor().getTitle() );
//  }

  @Test
  public void testOpenArchiveEntryByContextMenu() {
    projectExplorerBot.showView();
    SWTBotTree tree = projectExplorerBot.getTree();
    SWTBotTreeItem fileItem = selectArchiveEntry( tree );
    fileItem.contextMenu( "Open" ).click();

    assertEquals( TestArchiveFile.ENTRY_FILE_NAME, workbenchBot.activeEditor().getTitle() );
  }

  @Test
  public void testOpenSameEntryReusesEditor() {
    projectExplorerBot.showView();
    SWTBotTree tree = projectExplorerBot.getTree();
    SWTBotTreeItem fileItem = selectArchiveEntry( tree );
    fileItem.doubleClick();
    fileItem.doubleClick();

    assertEquals( 1, workbenchBot.editors().size() );
  }

  @Before
  public void setUp() throws Exception {
    testProject = new TestProject();
    TestArchiveFile.provision( testProject.getProject(), ARCHIVE_ZIP );
    workbenchBot = new SWTWorkbenchBot();
    projectExplorerBot = new ProjectExplorerBot( workbenchBot );
  }

  @After
  public void tearDown() throws CoreException {
    testProject.delete();
    workbenchBot.resetWorkbench();
  }

  private SWTBotTreeItem selectArchiveEntry( SWTBotTree tree ) {
    SWTBotTreeItem archiveItem = tree.expandNode( testProject.getName(), ARCHIVE_ZIP );
    return archiveItem.getNode( TestArchiveFile.ENTRY_FILE_NAME );
  }
}
