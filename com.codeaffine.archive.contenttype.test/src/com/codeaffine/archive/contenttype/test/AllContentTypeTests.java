package com.codeaffine.archive.contenttype.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.codeaffine.archive.contenttype.ContentTypesTest;
import com.codeaffine.archive.contenttype.internal.ZipContentDescriberTest;


@RunWith(Suite.class)
@SuiteClasses({
  ContentTypesTest.class,
  ZipContentDescriberTest.class,
})
public class AllContentTypeTests {
}
