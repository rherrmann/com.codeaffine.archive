package com.codeaffine.archive.ui.internal;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class ArchiveUiActivator extends AbstractUIPlugin {

  private static ArchiveUiActivator plugin;

  public static ArchiveUiActivator getDefault() {
    return plugin;
  }

  @Override
  public void start( BundleContext context ) throws Exception {
    super.start( context );
    plugin = this;
  }

  @Override
  public void stop( BundleContext context ) throws Exception {
    plugin = null;
    super.stop( context );
  }
}
