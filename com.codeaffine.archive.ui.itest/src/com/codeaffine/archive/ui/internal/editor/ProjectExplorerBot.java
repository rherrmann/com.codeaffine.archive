package com.codeaffine.archive.ui.internal.editor;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.ui.navigator.resources.ProjectExplorer;


class ProjectExplorerBot {
  private final SWTWorkbenchBot workbenchBot;
  private SWTBotView view;

  ProjectExplorerBot( SWTWorkbenchBot workbenchBot ) {
    this.workbenchBot = workbenchBot;
  }

  void showView() {
    ensureView();
    view.show();
  }

  SWTBotTree getTree() {
    ensureView();
    return view.bot().tree();
  }

  private void ensureView() {
    view = workbenchBot.viewById( ProjectExplorer.VIEW_ID );
  }
}
