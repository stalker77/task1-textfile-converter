/*
* This class...
*
* Created by Evgeny Dobrokvashin (aka Stalker) on 22.09.2015 
*
* Copyright (c) 2015 MegaFon, All Rights Reserved.
*/

package ru.tander.tasks.task1.sourcelist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileSystemLoopException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgeny Dobrokvashin
 *         Created by Stalker on 22.09.2015.
 * @version 1.0 Sep 2015
 */
public class FileVisitor extends SimpleFileVisitor<Path> {
  private static final Logger LOG = LoggerFactory.getLogger(FileVisitor.class);

  private static final String FILE_EXT_TO_WORK = "xml";
  private static final String EXT_SEPARATOR = ".";

  private String getFileNameExt(String fileNameWithExt) {
    int dot = fileNameWithExt.lastIndexOf(EXT_SEPARATOR);
    return fileNameWithExt.substring(dot + 1);
  }

  private List<String> fileList = null;

  public FileVisitor() {
    fileList = new ArrayList<>();
  }

  public List<String> getFileList() {
    return fileList;
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
    if (attr.isRegularFile() &&
        //file.getFileName().toString().equals("S1-FGF03A-01.xml")) {
        getFileNameExt(file.getFileName().getFileName().toString()).equals(FILE_EXT_TO_WORK)) {
      fileList.add(file.toString());
    }

    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult visitFileFailed(Path file, IOException e) {
    if (e instanceof FileSystemLoopException) {
      LOG.error("cycle detected: " + file, e);
    }
    else {
      LOG.error("", e);
    }
    return FileVisitResult.CONTINUE;
  }
}
