package com.codeaffine.archive.ui.internal.editor;

import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.codeaffine.archive.testfixture.TestProject;


@RunWith(SWTBotJunit4ClassRunner.class)
public class ExtractTest {
  private static final String EXTRACTED_FOLDER = "extracted";
  private static final String ARCHIVE_ZIP = "archive.zip";

  private TestProject testProject;
  private SWTWorkbenchBot workbenchBot;
  private ProjectExplorerBot projectExplorerBot;

  @Test
  public void testExtractToWorkspace() throws CoreException {
    testProject.createFolder( EXTRACTED_FOLDER );
    projectExplorerBot.showView();
    SWTBotTreeItem archiveItem = selectArchiveItem();
    archiveItem.contextMenu( "E&xtract..." ).click();
    SWTBot dialogBot = workbenchBot.activeShell().bot();
    dialogBot.textWithLabel( "Location" ).setText( getExtractLocation() );
    dialogBot.button( IDialogConstants.OK_LABEL ).click();
    workbenchBot.sleep( 2000 );

    IPath extractedFilePath = new Path( EXTRACTED_FOLDER ).append( "file.txt" );
    IResource extractedFile = findInProject( extractedFilePath );
    assertTrue( "Extracted file not found: " + extractedFilePath, extractedFile.exists() );
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

  private String getExtractLocation() {
    return "/" + testProject.getName() + "/" + EXTRACTED_FOLDER;
  }

  private SWTBotTreeItem selectArchiveItem() {
    SWTBotTree tree = projectExplorerBot.getTree();
    SWTBotTreeItem projectItem = tree.expandNode( testProject.getName() );
    SWTBotTreeItem archiveItem = projectItem.getNode( ARCHIVE_ZIP ).select();
    return archiveItem;
  }

  private IResource findInProject( IPath path ) {
    IWorkspace workspace = ResourcesPlugin.getWorkspace();
    return workspace.getRoot().findMember( new Path( testProject.getName() ).append( path ) );
  }
}
