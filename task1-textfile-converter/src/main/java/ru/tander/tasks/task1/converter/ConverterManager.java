/*
* This class...
*
* Created by Evgeny Dobrokvashin (aka Stalker) on 26.09.2015 
*
* Copyright (c) 2015 MegaFon, All Rights Reserved.
*/

package ru.tander.tasks.task1.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.tander.tasks.task1.sourcelist.SourceListException;
import ru.tander.tasks.task1.sourcelist.SourceList;
import ru.tander.tasks.task1.resultwriter.ResultWriterException;
import ru.tander.tasks.task1.resultwriter.ResultWriter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Evgeny Dobrokvashin
 *         Created by Stalker on 26.09.2015.
 * @version 1.0 Sep 2015
 */
public class ConverterManager {
  private static final Logger LOG = LoggerFactory.getLogger(ConverterManager.class);

  private SourceList sourceList = null;

  private Converter converter = null;

  private ResultWriter resultWriter = null;

  public void run() throws SourceListException, ResultWriterException {
    System.out.println("Text file converter...");
    System.out.format("Begin scan directories...%n");

    Path startDir = Paths.get(System.getProperty("user.dir"));
    sourceList.setStartDir(startDir);
    List<String> sources = sourceList.getSourceList();

    try {
      for (String source : sources) {
        converter.setSourceFile(source);
        System.out.format("Processing source: %s...", source);
        try {
          converter.convert();
        } catch (ConverterException e) {
          LOG.error("Error during convert.", e);
        }
        System.out.format(" Done.%n");
      }
    } finally {
      resultWriter.close();
    }

    System.out.format("End scan directories.%nProcessed %d sources.%n%n", sources.size());
  }

  public void setSourceList(SourceList sourceList) {
    this.sourceList = sourceList;
  }

  public void setConverter(Converter converter) {
    this.converter = converter;
  }

  public void setResultWriter(ResultWriter resultWriter) {
    this.resultWriter = resultWriter;
  }
}
