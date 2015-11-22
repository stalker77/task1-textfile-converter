package ru.tander.tasks.task1.sourcelist;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Evgeny Dobrokvashin
 *         Created by Stalker on 26.09.2015.
 * @version 1.0 Sep 2015
 */
public interface SourceList {
  List<String> getSourceList() throws SourceListException;

  void setStartDir(Path startDir);
}
