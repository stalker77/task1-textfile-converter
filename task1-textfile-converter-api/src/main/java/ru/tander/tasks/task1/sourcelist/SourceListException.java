package ru.tander.tasks.task1.sourcelist;

/**
 * @author Evgeny Dobrokvashin
 *         Created by Stalker on 26.09.2015.
 * @version 1.0 Sep 2015
 */
public class SourceListException extends Exception {
  public SourceListException(String message) {
    super(message);
  }

  public SourceListException(String message, Throwable cause) {
    super(message, cause);
  }

  public SourceListException(Throwable cause) {
    super(cause);
  }
}