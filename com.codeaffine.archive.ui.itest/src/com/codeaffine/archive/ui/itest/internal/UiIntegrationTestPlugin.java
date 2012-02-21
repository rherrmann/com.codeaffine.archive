package com.codeaffine.archive.ui.itest.internal;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;



public class UiIntegrationTestPlugin implements BundleActivator {

  public void start( BundleContext context ) throws Exception {
    SWTBotPreferences.SCREENSHOTS_DIR = "swtbot-screenshots";
System.out.println( "Curent keyboard layout: " + SWTBotPreferences.KEYBOARD_LAYOUT );
    SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US";
//    SWTBotPreferences.KEYBOARD_STRATEGY = "org.eclipse.swtbot.swt.finder.keyboard.SWTKeyboardStrategy";
  }

  public void stop( BundleContext context ) throws Exception {
  }
}
