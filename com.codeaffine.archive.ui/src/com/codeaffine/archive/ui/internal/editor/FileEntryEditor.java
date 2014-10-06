package com.codeaffine.archive.ui.internal.editor;

import java.io.IOException;

import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.PlatformUI;

import com.codeaffine.archive.ui.internal.model.FileEntry;
import com.codeaffine.archive.ui.internal.util.ContentTypeUtil;


class FileEntryEditor {
  private final FileEntry fileEntry;
  private final IEditorRegistry editorRegistry;
  private IContentDescription contentDescription;

  FileEntryEditor( FileEntry fileEntry ) {
    this.fileEntry = fileEntry;
    this.editorRegistry = PlatformUI.getWorkbench().getEditorRegistry();
  }

  IEditorInput createEditorInput() {
    return new ArchiveEntryEditorInput( fileEntry, getContentType() );
  }

  IEditorDescriptor getEditorDescriptor() {
    String fileName = fileEntry.getName();
    IEditorDescriptor result = editorRegistry.getDefaultEditor( fileName, getContentType() );
    if( result == null ) {
      result = editorRegistry.findEditor( "org.eclipse.ui.DefaultTextEditor" );
    }
    return result;
  }

  private IContentType getContentType() {
    initializeContentDescription();
    return contentDescription.getContentType();
  }

  private void initializeContentDescription() {
    if( contentDescription == null ) {
      contentDescription = getContentDescription();
    }
  }

  private IContentDescription getContentDescription() {
    try {
      return ContentTypeUtil.getContentDescription( fileEntry );
    } catch( IOException ioe ) {
      throw new RuntimeException( ioe );
    }
  }
}
