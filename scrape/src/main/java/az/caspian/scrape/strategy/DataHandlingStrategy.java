package az.caspian.scrape.strategy;

import az.caspian.core.tree.DataTable;

public interface DataHandlingStrategy {
  String SERIALIZED_FILE_EXTENSION = ".ser";

  void handle(DataTable data);
}
