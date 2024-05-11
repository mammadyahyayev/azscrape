package az.caspian.client;

import az.caspian.client.ui.frame.ClientTaskFrame;
import az.caspian.core.task.Task;
import org.junit.jupiter.api.Test;

class FrameTest {

  @Test
  void testClientTaskFrame() throws InterruptedException {
    var task = new Task("#1", "Test", null, null);
    new ClientTaskFrame(task);
    Thread.sleep(20000);
  }

}
