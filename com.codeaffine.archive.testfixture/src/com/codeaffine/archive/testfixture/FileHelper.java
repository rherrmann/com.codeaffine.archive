package com.codeaffine.archive.testfixture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;



public final class FileHelper {

  public static File getTempDirectory( String name ) {
    return new File( System.getProperty( "java.io.tmpdir" ), name );
  }

  public static void copy( InputStream inputStream, File destination ) throws IOException {
    FileOutputStream outputStream = new FileOutputStream( destination );
    int read = inputStream.read();
    while( read != -1 ) {
      outputStream.write( read );
      read = inputStream.read();
    }
    outputStream.close();
  }

  public static byte[] read( File file ) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    FileInputStream inputStream = new FileInputStream( file );
    try {
      int read = inputStream.read();
      while( read != -1 ) {
        read = inputStream.read();
        outputStream.write( read );
      }
    } finally {
      inputStream.close();
    }
    return outputStream.toByteArray();
  }

  public static void delete( File file ) {
    if( file.exists() ) {
      deleteDirectory( file );
      deleteFile( file );
    }
  }

  private static void deleteDirectory( File file ) {
    if( file.isDirectory() ) {
      File[] children = file.listFiles();
      for( File child : children ) {
        delete( child );
      }
    }
  }

  private static void deleteFile( File file ) {
    boolean deleted = file.delete();
    if( !deleted ) {
      throw new IllegalStateException( "Failed to delete: " + file );
    }
  }

  private FileHelper() {
    // prevent instantiation
  }
}
