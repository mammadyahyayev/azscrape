package az.caspian.export;

import az.caspian.core.model.DataFile;
import az.caspian.core.tree.DataTable;

/** Exports report files */
public interface Exporter {

  /**
   * Exports scraped data into appropriate file
   *
   * @param dataFile exported file
   * @param reportData class contains scraped data
   */
  void export(DataFile dataFile, DataTable reportData);
}
