/*
* This class developed for main function of application
*
* Created by Evgeny Dobrokvashin (aka Stalker) on 22.09.15
*
* Copyright (c) 2015 Tander, All Rights Reserved.
*/
package ru.tander.tasks.task1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.tander.tasks.task1.config.ApplicationInitializer;
import ru.tander.tasks.task1.converter.ConverterManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Main App
 *
 * @author Evgeny Dobrokvashin
 * @version 1.0 sep 2015
 *
 * Created by Stalker on 22.09.15.
 */
public class Main {
  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  public static void main( String[] args ) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("JRE Version: " + System.getProperty("java.version"));
      LOG.debug("Enter to task1()");
    }

    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      ApplicationInitializer.configure();

      ApplicationContext context = new ClassPathXmlApplicationContext("file:./beans.xml");

      ConverterManager converterManager = (ConverterManager)context.getBean("converterManager");
      converterManager.run();

      System.out.println("For close app press Enter key...");
      br.readLine();
    } catch (Exception e) {
      LOG.error("General error: ", e);
    } finally {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Exit from main()");
      }
    }
  }
}
