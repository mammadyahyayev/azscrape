package az.caspian.core.tree.node;

public class ActionNode extends Node {
  private final String name;
  private final String selector;
  private final Action actionType;

  public ActionNode(String name, String selector, Action actionType) {
    this.name = name;
    this.selector = selector;
    this.actionType = actionType;
  }

  public String getName() {
    return name;
  }

  public String getSelector() {
    return selector;
  }

  public Action getActionType() {
    return actionType;
  }
}
