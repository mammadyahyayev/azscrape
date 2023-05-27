package az.caspian.core.tree;

import az.caspian.core.tree.DataNodeLocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataNodeLocationTest {

    @Test
    void testNextLocation_whenLastLocationGiven_returnLocationAppropriateLocation() {
        DataNodeLocation location1 = new DataNodeLocation("A", 3);

        DataNodeLocation actualInSameLevel = location1.nextLocation();
        DataNodeLocation expectedInSameLevel = new DataNodeLocation("A", 4);

        DataNodeLocation actualInNextLevel = location1.nextLocation(true);
        DataNodeLocation expectedInNextLevel = new DataNodeLocation("B", 0);

        assertEquals(expectedInSameLevel, actualInSameLevel);
        assertEquals(expectedInSameLevel.getLevel(), actualInSameLevel.getLevel());
        assertEquals(expectedInSameLevel.getOrder(), actualInSameLevel.getOrder());

        assertEquals(expectedInNextLevel, actualInNextLevel);
        assertEquals(expectedInNextLevel.getLevel(), actualInNextLevel.getLevel());
        assertEquals(expectedInNextLevel.getOrder(), actualInNextLevel.getOrder());
    }

}