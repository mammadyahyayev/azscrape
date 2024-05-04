package az.caspian.core.tree.node;

public abstract class Node {
  public boolean isDataNode() {
    return this instanceof DataNode;
  }

  public boolean isParentNode() {
    return this instanceof ParentNode;
  }

  public boolean isKeyValueNode() {
    return this instanceof KeyValueDataNode;
  }

  public boolean isListNode() {
    return this instanceof ListNode;
  }

  public boolean isLinkNode() {
    return this instanceof LinkNode;
  }

  public boolean isTimeoutNode() {
    return this instanceof TimeoutNode;
  }
}
