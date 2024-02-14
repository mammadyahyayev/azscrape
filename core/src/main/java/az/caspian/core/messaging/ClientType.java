package az.caspian.core.messaging;

/**
 * The type of client which assigned to identify his role on projects.
 */
public enum ClientType {
  /**
   * The project owner or the client who shares his project publicly
   * for other {@link #WORKER} Clients can join.
   * <b>Note:</b> {@link #WORKER} type will be a default type for Clients.
   * However, if a Client shares his project publicly, then it will become
   * {@link #COORDINATOR} and maintainer of his project.
   */
  COORDINATOR,

  /**
   * This is the default Client type.
   * The type of client who will do whatever jobs assigned to him
   * and will be in sync or async communication with {@link #COORDINATOR}
   * to report status of the job, to send collected data and so on.
   */
  WORKER
}
