package az.caspian.scrape;

import az.caspian.core.tree.node.InterventionNode;
import az.caspian.core.tree.node.NotificationMethod;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class NodeExecutorTest {

  @Test
  void executeInterventionNode() {
    var interventionNode = new InterventionNode(20, TimeUnit.SECONDS, NotificationMethod.CONSOLE);

    var executor = new NodeExecutor();
    executor.executeInterventionNode(interventionNode);
  }
}