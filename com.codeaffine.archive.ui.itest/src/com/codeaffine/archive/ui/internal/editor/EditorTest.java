package com.codeaffine.archive.ui.internal.editor;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.keyboard.Keystrokes;
import org.eclipse.swtbot.swt.finder.matchers.AllOf;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.codeaffine.archive.testfixture.TestProject;


@RunWith(SWTBotJunit4ClassRunner.class)
public class EditorTest {
  private static final String ARCHIVE_ZIP = "archive.zip";
  private static final String FILE_TXT = "file.txt";

  private TestProject testProject;
  private SWTWorkbenchBot bot;

  @Test
  public void testOpenArchiveEntryByDoubleClick() {
    SWTBotTree tree = getProjectExplorerTree();
    SWTBotTreeItem fileItem = selectArchiveEntry( tree );
    fileItem.doubleClick();

    assertEquals( FILE_TXT, bot.activeEditor().getTitle() );
  }

// TODO [rh] Disabled until bug 372170 (pressShortcut( Keystrokes.CR ) fails with "Invalid key code")
//      is resolved
//  @Test
//  public void testOpenArchiveEntryByReturnKey() {
//    SWTBotTree tree = getProjectExplorerTree();
//    SWTBotTreeItem fileItem = selectArchiveEntry( tree );
//    fileItem.pressShortcut( Keystrokes.CR );
//
//    assertEquals( FILE_TXT, bot.activeEditor().getTitle() );
//  }

  @Test
  public void testOpenArchiveEntryByContextMenu() {
    SWTBotTree tree = getProjectExplorerTree();
    SWTBotTreeItem fileItem = selectArchiveEntry( tree );
    fileItem.contextMenu( "Open" ).click();

    assertEquals( FILE_TXT, bot.activeEditor().getTitle() );
  }

  @Test
  public void testOpenSameEntryReusesEditor() {
    SWTBotTree tree = getProjectExplorerTree();
    SWTBotTreeItem fileItem = selectArchiveEntry( tree );
    fileItem.doubleClick();
    fileItem.doubleClick();

    assertEquals( 1, bot.editors().size() );
  }

  @Before
  public void setUp() throws Exception {
    testProject = new TestProject();
    bot = new SWTWorkbenchBot();
    provisionArchiveFile();
  }

  @After
  public void tearDown() throws CoreException {
    testProject.delete();
    bot.resetWorkbench();
  }

  private void provisionArchiveFile() throws Exception {
    InputStream inputStream = getClass().getResourceAsStream( "archive.zip" );
    try {
      TestProject.createFile( testProject.getProject(), ARCHIVE_ZIP, inputStream );
    } finally {
      inputStream.close();
    }
  }

  private SWTBotTree getProjectExplorerTree() {
    bot.viewById( ProjectExplorer.VIEW_ID ).show();
    SWTBotView view = bot.viewById( ProjectExplorer.VIEW_ID );
    return new SWTBotTree( bot.widget( allTreesMatcher(), view.getWidget() ) );
  }

  private SWTBotTreeItem selectArchiveEntry( SWTBotTree tree ) {
    SWTBotTreeItem archiveItem = tree.expandNode( testProject.getName(), ARCHIVE_ZIP );
    return archiveItem.getNode( FILE_TXT );
  }

  @SuppressWarnings("unchecked")
  private static Matcher<Tree> allTreesMatcher() {
    return AllOf.allOf( WidgetMatcherFactory.widgetOfType( Tree.class ) );
  }
}
