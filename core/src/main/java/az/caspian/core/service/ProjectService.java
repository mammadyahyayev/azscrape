package az.caspian.core.service;

import az.caspian.core.constant.FileConstants;
import az.caspian.core.internal.ModuleManager;
import az.caspian.core.messaging.Client;
import az.caspian.core.messaging.ClientType;
import az.caspian.core.remote.Project;
import az.caspian.core.remote.Session;
import az.caspian.core.utils.Asserts;
import az.caspian.core.utils.DateUtils;
import az.caspian.core.utils.PropertiesFileSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Stream;

public class ProjectService {
  private static final Logger LOG = LogManager.getLogger(ProjectService.class);

  public void saveProject(Project project) {
    Asserts.required(project, "project is required!");

    /*
        1. Check project name
        2. Check if there is already a project with this name, if no continues, if yes error
        3. Create a folder and put required data into it.
     */
  }

  public Project shareProject(String projectName) {
    Asserts.required(projectName, "projectName must not be null or empty!");

    try (Stream<Path> directoryStream = Files.list(FileConstants.APP_PATH)) {
      List<String> projectsName = directoryStream
        .filter(Files::isDirectory)
        .map(Path::getFileName)
        .map(Path::toString)
        .toList();

      if (!projectsName.contains(projectName)) {
        throw new IllegalArgumentException("Project '" + projectName + "' does not exist!");
      }

      //TODO: Check if there is already shared project or not, if user clicks second time.
      FutureTask<Boolean> runServerModuleTask = new FutureTask<>(
        () -> ModuleManager.runServerModule(Map.of("-p", projectName)));
      new Thread(runServerModuleTask).start();

      boolean isRunning = runServerModuleTask.get();
      if (isRunning) {
        Client currentClient = Session.getCurrentClient();
        currentClient.setClientType(ClientType.COORDINATOR);
        Session.setCurrentClient(currentClient);

        var project = findProjectByName(projectName);
        Session.setCurrentProject(project);
        return project;
      }
    } catch (IOException e) {
      LOG.error("Failed to read projects from {}", FileConstants.APP_PATH);
    } catch (ExecutionException e) {
      LOG.error("Failed to run server module!");
    } catch (InterruptedException e) {
      LOG.error("Task is interrupted!");
    }

    return null;
  }

  public Project findProjectByName(final String projectName) {
    Asserts.required(projectName, "projectName is required!");
    var projectPropertiesFilePath = FileConstants.APP_PATH.resolve(projectName)
      .resolve("project.properties");
    var properties = new PropertiesFileSystem().load(projectPropertiesFilePath);

    var project = new Project();
    project.setName(projectName);

    var createdAt = LocalDateTime.parse((String) properties.get("createdAt"), DateUtils.DEFAULT_DATE_FORMAT);
    project.setCreatedAt(createdAt);

    Client currentClient = Session.getCurrentClient();
    var createdBy = (String) properties.get("createdBy");
    project.setCreatedBy(createdBy.equals(currentClient.getFullName()) ? currentClient : null);

    return project;
  }
}
