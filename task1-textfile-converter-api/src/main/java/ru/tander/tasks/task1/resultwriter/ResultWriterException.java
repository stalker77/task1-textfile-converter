/*
* This class...
*
* Created by Evgeny Dobrokvashin (aka Stalker) on 25.09.2015 
*
* Copyright (c) 2015 MegaFon, All Rights Reserved.
*/

package ru.tander.tasks.task1.resultwriter;

/**
 * @author Evgeny Dobrokvashin
 *         <p/>
 *         Created by Stalker on 25.09.2015.
 * @version 1.0 Сент. 2015
 */
public class ResultWriterException extends Exception {
  public ResultWriterException() {
    super();
  }

  public ResultWriterException(String message) {
    super(message);
  }

  public ResultWriterException(String message, Throwable cause) {
    super(message, cause);
  }
}
