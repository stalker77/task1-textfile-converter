package ru.tander.tasks.task1.converter;

/**
 * @author Evgeny Dobrokvashin
 *         Created by Stalker on 26.09.2015.
 * @version 1.0 Сент. 2015
 */
public interface Converter {
  void setSourceFile(String sourceFile);

  void convert() throws ConverterException;
}
