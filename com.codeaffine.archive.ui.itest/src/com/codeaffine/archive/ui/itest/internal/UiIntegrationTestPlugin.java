package com.codeaffine.archive.ui.itest.internal;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;



public class UiIntegrationTestPlugin implements BundleActivator {

  public void start( BundleContext context ) throws Exception {
    SWTBotPreferences.SCREENSHOTS_DIR = "swtbot-screenshots";
    SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US";
  }

  public void stop( BundleContext context ) throws Exception {
  }
}
