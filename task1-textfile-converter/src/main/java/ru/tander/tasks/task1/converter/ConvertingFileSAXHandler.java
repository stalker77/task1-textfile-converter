/*
* This class developed to realise converted file SAX handler
*
* Created by Evgeny Dobrokvashin (aka Stalker) on 22.09.2015 
*
* Copyright (c) 2015 MegaFon, All Rights Reserved.
*/

package ru.tander.tasks.task1.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.tander.tasks.task1.config.ApplicationConfig;
import ru.tander.tasks.task1.resultwriter.ResultWriterException;
import ru.tander.tasks.task1.resultwriter.ResultWriter;

/**
 * @author Evgeny Dobrokvashin
 *         Created by Stalker on 22.09.2015.
 * @version 1.0 sep 2015
 */
public class ConvertingFileSAXHandler extends DefaultHandler {
  private static final Logger LOG = LoggerFactory.getLogger(ConvertingFileSAXHandler.class);

  private int currentNodeLevel = -1;

  private String folderName = null;

  private ResultWriter resultWriter = null;

  public ConvertingFileSAXHandler(String folderName) {
    this.folderName = folderName;
  }

  public void setResultWriter(ResultWriter resultWriter) {
    this.resultWriter = resultWriter;
  }

  public void startElement(String namespaceURI,
                           String localName,
                           String qName,
                           Attributes atts) throws SAXException {
    currentNodeLevel++;

    if (qName.toUpperCase().equals("MANUALS")) {
      processManuals(qName, atts);
    } else if (qName.startsWith("PgGSP")) {
      processPgGSPNode(qName, atts);
    }
  }

  private void processManuals(String qName, Attributes atts) throws SAXException {
    if (0 != currentNodeLevel) {
      throw new SAXException("Node " + qName + " invalid location!");
    }
  }

  private void processPgGSPNode(String qName, Attributes atts) throws SAXException {
    if (0 == currentNodeLevel) {
      throw new SAXException("Root node invalid! Expected: PgGSP");
    }

    if (atts != null) {
      int attrIndex = atts.getIndex("PInfo");
      if (attrIndex < 0) {
        throw new SAXException(String.format("Attribute '%2$s' in '%1$s' node missing!", qName, "PInfo"));
      }

      String attrValue = atts.getValue(attrIndex);
      String fileString = ApplicationConfig.SiteName + "\"" + folderName + "\"/" + getValue(attrValue) + ".html";
      if (null == resultWriter) {
        throw new SAXException("ResultWriter not set.");
      }

      try {
        resultWriter.writeString(fileString + "\n");
      } catch (ResultWriterException e) {
        throw new SAXException("Error on write to file: " + ApplicationConfig.OutputFileName, e);
      }
    }
    else {
      throw new SAXException("Attributes missing!");
    }
  }

  private String getValue(String attrValue) {
    String result = "";

    int iPos = attrValue.lastIndexOf('|');

    if (iPos > -1) {
      int iPosPrior = attrValue.lastIndexOf('|', iPos - 1);
      if (iPosPrior > -1) {
        result = attrValue.substring(iPosPrior + 1, iPos);
      }
    }

    return result;
  }

  public void endElement(String uri, String localName, String qName) throws SAXException {
    currentNodeLevel--;
  }
}
