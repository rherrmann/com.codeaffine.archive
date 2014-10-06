package com.codeaffine.archive.ui.internal.extract;

import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;


public interface ExtractWriter {
  void write( IPath path, InputStream inputStream ) throws CoreException;
}
