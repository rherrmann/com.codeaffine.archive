package com.codeaffine.archive.ui.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.codeaffine.archive.ui.internal.editor.ArchiveDragAdapterAssistantTest;
import com.codeaffine.archive.ui.internal.editor.ArchiveEntryEditorInputTest;
import com.codeaffine.archive.ui.internal.editor.ArchiveEntryStorageTest;
import com.codeaffine.archive.ui.internal.editor.EditorOpenerTest;
import com.codeaffine.archive.ui.internal.editor.FileEntryDragSupportTest;
import com.codeaffine.archive.ui.internal.editor.FileEntryEditorTest;
import com.codeaffine.archive.ui.internal.editor.OpenActionProviderTest;
import com.codeaffine.archive.ui.internal.editor.OpenActionTest;
import com.codeaffine.archive.ui.internal.editor.PersistableEditorInputFactoryTest;
import com.codeaffine.archive.ui.internal.extract.ArchiveExtractorJobTest;
import com.codeaffine.archive.ui.internal.extract.ArchiveExtractorTest;
import com.codeaffine.archive.ui.internal.extract.ExtractEntryActionTest;
import com.codeaffine.archive.ui.internal.extract.ExtractDialogTest;
import com.codeaffine.archive.ui.internal.extract.ExtractLocationTest;
import com.codeaffine.archive.ui.internal.extract.FileEntryCounterTest;
import com.codeaffine.archive.ui.internal.extract.FilesystemWriterTest;
import com.codeaffine.archive.ui.internal.extract.WorkspaceFolderFilterTest;
import com.codeaffine.archive.ui.internal.extract.WorkspaceFolderValidatorTest;
import com.codeaffine.archive.ui.internal.extract.WorkspaceWriterTest;
import com.codeaffine.archive.ui.internal.model.ArchiveEntryTest;
import com.codeaffine.archive.ui.internal.model.ArchiveReaderTest;
import com.codeaffine.archive.ui.internal.model.ArchiveTest;
import com.codeaffine.archive.ui.internal.model.DirectoryEntryTest;
import com.codeaffine.archive.ui.internal.model.FileEntryTest;
import com.codeaffine.archive.ui.internal.util.ContentTypeUtilTest;
import com.codeaffine.archive.ui.internal.util.StatusUtilTest;
import com.codeaffine.archive.ui.internal.viewer.ArchiveEntrySorterTest;
import com.codeaffine.archive.ui.internal.viewer.ArchiveLabelProviderTest;


@RunWith(Suite.class)
@SuiteClasses({
  ArchiveTest.class,
  ArchiveEntryTest.class,
  FileEntryTest.class,
  DirectoryEntryTest.class,
  ArchiveReaderTest.class,
  ArchiveLabelProviderTest.class,
  ArchiveEntrySorterTest.class,
  OpenActionTest.class,
  OpenActionProviderTest.class,
  EditorOpenerTest.class,
  ArchiveEntryEditorInputTest.class,
  ArchiveEntryStorageTest.class,
  StatusUtilTest.class,
  ContentTypeUtilTest.class,
  ExtractEntryActionTest.class,
  ArchiveExtractorTest.class,
  ArchiveExtractorJobTest.class,
  WorkspaceFolderFilterTest.class,
  WorkspaceFolderValidatorTest.class,
  ExtractDialogTest.class,
  ExtractLocationTest.class,
  WorkspaceWriterTest.class,
  FilesystemWriterTest.class,
  FileEntryCounterTest.class,
  PersistableEditorInputFactoryTest.class,
  FileEntryDragSupportTest.class,
  ArchiveDragAdapterAssistantTest.class,
  FileEntryEditorTest.class,
})
public class AllUiTests {
}
