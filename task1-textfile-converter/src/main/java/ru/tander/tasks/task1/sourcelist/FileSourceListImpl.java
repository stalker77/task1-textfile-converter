/*
* This class...
*
* Created by Evgeny Dobrokvashin (aka Stalker) on 26.09.2015 
*
* Copyright (c) 2015 MegaFon, All Rights Reserved.
*/

package ru.tander.tasks.task1.sourcelist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Evgeny Dobrokvashin
 *         Created by Stalker on 26.09.2015.
 * @version 1.0 Sep 2015
 */
public class FileSourceListImpl implements SourceList {
  private static final Logger LOG = LoggerFactory.getLogger(FileSourceListImpl.class);

  private Path startDir = null;

  public void setStartDir(Path startDir) {
    this.startDir = startDir;
  }

  public List<String> getSourceList() throws SourceListException {
    FileVisitor fileVisitor = new FileVisitor();
    try {
      Files.walkFileTree(startDir, fileVisitor);
    } catch (IOException e) {
      LOG.error("Error during get file list.", e);
      throw new SourceListException("Error during get file list.", e);
    }
    return fileVisitor.getFileList();
  }
}
