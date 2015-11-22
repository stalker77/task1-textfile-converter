package ru.tander.tasks.task1.resultwriter;

/**
 * @author Evgeny Dobrokvashin
 *         Created by Stalker on 24.09.2015.
 * @version 1.0 sep 2015
 */
public interface ResultWriter {
  void writeString(String strToWrite) throws ResultWriterException;

  void close() throws ResultWriterException;
}
