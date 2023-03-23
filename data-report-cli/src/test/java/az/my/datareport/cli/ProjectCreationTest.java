package az.my.datareport.cli;

import az.my.datareport.config.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectCreationTest {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenEmptyProject_whenEnterProjectName_returnProjectWithName() {
        // given
        Project project = new Project();
        ConsoleReader consoleReader = mock(ConsoleReader.class);

        // when
        when(consoleReader.readLine(anyString(), anyInt(), anyString())).thenReturn("Test");
        Step<Project> infoStep = new ProjectInfoStep(consoleReader);
        infoStep.execute(project);

        // then
        assertEquals("Test", project.getName());
    }
}