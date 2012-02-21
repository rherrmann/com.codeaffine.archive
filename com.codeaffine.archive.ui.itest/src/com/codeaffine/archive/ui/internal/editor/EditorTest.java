package com.codeaffine.archive.ui.internal.editor;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
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

  private TestProject testProject;
  private SWTWorkbenchBot bot;

  @Test
  public void testOpenArchiveEntryInEditor() throws Exception {
    provisionArchiveFile();
    SWTBotView view = bot.viewById( ProjectExplorer.VIEW_ID );
    view.show();
    SWTBotTree tree = new SWTBotTree( bot.widget( allTreesMatcher(), view.getWidget() ) );
    SWTBotTreeItem treeItem = tree.expandNode( testProject.getName(), "archive.zip" ).getNode( "file.txt" );
    treeItem.doubleClick();

    assertEquals( "file.txt", bot.activeEditor().getTitle() );
  }

  @Before
  public void setUp() {
    testProject = new TestProject();
    bot = new SWTWorkbenchBot();
  }

  @After
  public void tearDown() throws CoreException {
    testProject.delete();
    bot.resetWorkbench();
  }

  private void provisionArchiveFile() throws Exception {
    InputStream inputStream = getClass().getResourceAsStream( "archive.zip" );
    try {
      TestProject.createFile( testProject.getProject(), "archive.zip", inputStream );
    } finally {
      inputStream.close();
    }
  }

  @SuppressWarnings("unchecked")
  private static Matcher<Tree> allTreesMatcher() {
    return AllOf.allOf( WidgetMatcherFactory.widgetOfType( Tree.class ) );
  }
}
