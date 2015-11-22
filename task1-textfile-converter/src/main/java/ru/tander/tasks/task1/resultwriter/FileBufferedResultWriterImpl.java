/*
* This class...
*
* Created by Evgeny Dobrokvashin (aka Stalker) on 24.09.2015 
*
* Copyright (c) 2015 MegaFon, All Rights Reserved.
*/

package ru.tander.tasks.task1.resultwriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Evgeny Dobrokvashin
 *         Created by Stalker on 24.09.2015.
 * @version 1.0 Sep 2015
 */
public class FileBufferedResultWriterImpl implements ResultWriter {
  private static final Logger LOG = LoggerFactory.getLogger(FileBufferedResultWriterImpl.class);

  private int outBufferCapacity = 10000;

  private String outBuffer = "";

  private BufferedWriter bufferedWriter = null;

  public FileBufferedResultWriterImpl(String fileName) throws ResultWriterException {
    Path outputFile = Paths.get(fileName);
    try {
      bufferedWriter = Files.newBufferedWriter(outputFile, Charset.forName("utf-8"), StandardOpenOption.APPEND, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    } catch (IOException e) {
      LOG.error("Error during open output file: " + fileName, e);
    }
  }

  public void writeString(String strToWrite) throws ResultWriterException {
    try {
      if ((outBuffer.length() + strToWrite.length()) <= outBufferCapacity) {
        outBuffer += strToWrite;
      }
      else {
        if (null != bufferedWriter) {
          bufferedWriter.write(outBuffer);
        }
        else {
          throw new NullPointerException("bufferedWriter");
        }

        outBuffer = strToWrite;
      }
    } catch (IOException e) {
      LOG.error("Error during write to output file.", e);
      throw new ResultWriterException("Error during write to output file.", e);
    } catch (NullPointerException e) {
      LOG.error("bufferedWriter is null.", e);
      throw new ResultWriterException("bufferedWriter is null.", e);
    }
  }

  public void close() throws ResultWriterException {
    if (null != bufferedWriter) {
      try {
        if (outBuffer.length() > 0) {
          bufferedWriter.write(outBuffer);
          outBuffer = "";
        }

        bufferedWriter.flush();
        bufferedWriter.close();
      } catch (IOException e) {
        LOG.error("Error during close output file.", e);
        throw new ResultWriterException("Error during close output file.", e);
      }
    }
  }
}
