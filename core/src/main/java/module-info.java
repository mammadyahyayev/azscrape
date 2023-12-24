module azscrape.core {
  requires org.apache.logging.log4j;
  requires com.google.common;
  requires org.jetbrains.annotations;

  exports az.caspian.core;
  exports az.caspian.core.tree;
  exports az.caspian.core.utils;
  exports az.caspian.core.messaging;
  exports az.caspian.core.model;
  exports az.caspian.core.model.enumeration;
  exports az.caspian.core.constant;
}
