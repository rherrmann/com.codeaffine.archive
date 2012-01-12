package com.codeaffine.archive.ui.internal.util;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import com.codeaffine.archive.ui.internal.ArchiveUiActivator;

public class StatusUtil {

  public static void log( Throwable throwable ) {
    IStatus status = createStatus( throwable );
    StatusManager.getManager().handle( status, StatusManager.LOG );
  }

  public static void log( String message, Throwable throwable ) {
    IStatus status = createStatus( IStatus.ERROR, message, throwable );
    StatusManager.getManager().handle( status, StatusManager.LOG );
  }

  public static void log( int severity, String message ) {
    IStatus status = createStatus( severity, message, null );
    StatusManager.getManager().handle( status, StatusManager.LOG );
  }

  public static void show( Throwable exception ) {
    IStatus status = createStatus( exception );
    StatusManager.getManager().handle( status, StatusManager.SHOW | StatusManager.LOG );
  }

  public static IStatus createStatus( Throwable throwable ) {
    return createStatus( IStatus.ERROR, throwable.getMessage(), throwable );
  }

  public static IStatus createStatus( int severity, String message, Throwable throwable ) {
    return new Status( severity, getPluginId(), message, throwable );
  }

  private static String getPluginId() {
    return ArchiveUiActivator.getDefault().getBundle().getSymbolicName();
  }

  private StatusUtil() {
    // prevent instantiation
  }
}
