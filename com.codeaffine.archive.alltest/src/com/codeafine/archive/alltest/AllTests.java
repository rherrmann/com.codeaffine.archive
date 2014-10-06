package com.codeafine.archive.alltest;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.codeaffine.archive.contenttype.test.AllContentTypeTests;
import com.codeaffine.archive.ui.test.AllUiTests;


@RunWith(Suite.class)
@SuiteClasses({
  AllUiTests.class,
  AllContentTypeTests.class,
})
public class AllTests {
	// no comment
}
