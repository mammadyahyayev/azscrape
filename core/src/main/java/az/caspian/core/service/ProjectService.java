package az.caspian.core.service;

import az.caspian.core.constant.FileConstants;
import az.caspian.core.internal.ModuleManager;
import az.caspian.core.io.PropertiesFileSystem;
import az.caspian.core.messaging.Client;
import az.caspian.core.messaging.ClientType;
import az.caspian.core.remote.Project;
import az.caspian.core.remote.Session;
import az.caspian.core.utils.Asserts;
import az.caspian.core.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Stream;

public class ProjectService {
  private static final Logger LOG = LogManager.getLogger(ProjectService.class);

  public List<String> getAllProjectNames() {
    try (var files = Files.list(FileConstants.APP_PATH)) {
      return files
        .filter(Files::isDirectory)
        .map(file -> file.getFileName().toString())
        .toList();
    } catch (IOException e) {
      LOG.error("Failed to read files from " + FileConstants.APP_PATH);
    }

    return Collections.emptyList();
  }

  public void saveProject(Project project) {
    Asserts.required(project, "project is required!");

    List<String> projectNames = getAllProjectNames();
    boolean isExist = projectNames.contains(project.getName());
    if (!isExist) {
      //TODO: create a custom operation response class and return it
    }
  }

  public void saveAttendants(Project project) {
    var attendatsFilePath = FileConstants.APP_PATH.resolve(project.getName()).resolve("attendants.txt");

    try (var writer = new BufferedWriter(new FileWriter(attendatsFilePath.toFile()))) {
      for (Client attendant : project.getAttendants()) {
        writer.write(attendant.getFullName());
      }
    } catch (IOException e) {
      LOG.error("Failed to save attendants to file: {}", attendatsFilePath);
    }
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

      FutureTask<Boolean> runServerModuleTask = new FutureTask<>(
        () -> ModuleManager.runServerModule(Map.of("-p", projectName)));
      new Thread(runServerModuleTask).start();

      //TODO: Check if there is already shared project or not, if user clicks second time.
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
      LOG.error("Failed to execute server module!");
    } catch (InterruptedException e) {
      LOG.error("Executing server module task is interrupted!");
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

    loadAttendantsToProject(project);

    return project;
  }

  private void loadAttendantsToProject(Project project) {
    var attendantsFilePath = FileConstants.APP_PATH.resolve(project.getName())
      .resolve("attendants.txt");

    try {
      List<Client> attendants = Files.readAllLines(attendantsFilePath)
        .stream()
        .map((fullName) -> {
          String[] fullNameSplit = fullName.split(" ");
          return new Client(fullNameSplit[0], fullNameSplit[1]);
        })
        .toList();
      project.setAttendants(attendants);
    } catch (IOException e) {
      LOG.error("Failed to read attendants from " + attendantsFilePath);
    }
  }
}
