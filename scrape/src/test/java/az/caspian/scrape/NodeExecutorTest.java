package az.caspian.scrape;

import az.caspian.core.tree.node.InterventionNode;
import az.caspian.core.tree.node.NotificationMethod;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class NodeExecutorTest {

  @Test
  void executeInterventionNode() {
    var interventionNode1 = new InterventionNode(40, TimeUnit.SECONDS, NotificationMethod.CONSOLE);
    var interventionNode2 = new InterventionNode(20, TimeUnit.SECONDS, NotificationMethod.CONSOLE);

    var executor = new NodeExecutor();
    executor.executeInterventionNode(interventionNode1);
    executor.executeInterventionNode(interventionNode2);
  }
}