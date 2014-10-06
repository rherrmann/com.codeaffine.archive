package com.codeaffine.archive.ui.internal.model;

import org.eclipse.core.runtime.IPath;


interface ArchiveLocationProvider {
  String getArchiveName();
  IPath getLocation();
}
