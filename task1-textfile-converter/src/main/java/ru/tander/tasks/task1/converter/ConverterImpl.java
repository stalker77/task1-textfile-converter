/*
* This class developed for realize Converter class
*
* Created by Evgeny Dobrokvashin (aka Stalker) on 22.09.2015 
*
* Copyright (c) 2015 MegaFon, All Rights Reserved.
*/

package ru.tander.tasks.task1.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import ru.tander.tasks.task1.resultwriter.ResultWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Evgeny Dobrokvashin
 *         Created by Stalker on 22.09.2015.
 * @version 1.0 Sep 2015
 */
public class ConverterImpl implements Converter {
  private static final Logger LOG = LoggerFactory.getLogger(ConverterImpl.class);

  private String sourceFile = null;

  private ResultWriter resultWriter = null;

  public ConverterImpl(ResultWriter resultWriter) {
    if (null == resultWriter) {
      throw new NullPointerException("resultWriter");
    }

    this.resultWriter = resultWriter;
  }

  public void setSourceFile(String sourceFile) {
    this.sourceFile = sourceFile;
  }

  public void convert() throws ConverterException {
    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
      XMLReader xmlReader = saxParser.getXMLReader();

      Path sourceFilePath = Paths.get(sourceFile);
      String folderName = sourceFilePath.getName(sourceFilePath.getNameCount() - 2).toString();
      ConvertingFileSAXHandler convertingFileSAXHandler = new ConvertingFileSAXHandler(folderName);
      convertingFileSAXHandler.setResultWriter(resultWriter);
      xmlReader.setContentHandler(convertingFileSAXHandler);

      try (BufferedReader br = Files.newBufferedReader(sourceFilePath, Charset.forName("utf-8"))) {
        xmlReader.parse(new InputSource(br));
      }
    } catch (ParserConfigurationException e) {
      LOG.error("Error at get new SAX parser.", e);
      throw new ConverterException("Error at get new SAX parser.", e);
    } catch (SAXException e) {
      LOG.error("Error in SAX factory.", e);
      throw new ConverterException("Error in SAX factory.", e);
    } catch (IOException e) {
      LOG.error("Error in file IO.", e);
      throw new ConverterException("Error in file IO.", e);
    }
  }
}
