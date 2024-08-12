package me.mertunctuncer.peorm.reflection;

import me.mertunctuncer.peorm.util.Pair;
import me.mertunctuncer.peorm.model.ColumnProperties;
import me.mertunctuncer.peorm.mock.NamedMockTable;
import me.mertunctuncer.peorm.mock.NamelessMockTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassParserTest {

    @Test
    void reader_should_throw_npe_if_annotation_is_not_present() {
        assertThrows(NullPointerException.class, () -> ClassScanner.getTableName(Object.class));
        Assertions.assertDoesNotThrow(() -> ClassScanner.getTableName(NamedMockTable.class));
    }

    @Test
    void table_name_should_be_read_correctly() {
        assertEquals(ClassScanner.getTableName(NamedMockTable.class), "named-mock-table");
        assertEquals(ClassScanner.getTableName(NamelessMockTable.class), "namelessmocktable");
    }

    @Test
    void fields_should_be_read_correctly() {
        List<Pair<Field, ColumnProperties>> fieldData = ClassScanner.mapFields(NamedMockTable.class);

        assertEquals(3, fieldData.size());

        ColumnProperties id = fieldData.get(0).second();
        ColumnProperties count = fieldData.get(1).second();
        ColumnProperties name = fieldData.get(2).second();

        assertEquals(id.name(), "no");
        assertTrue(id.primaryKey());
        assertNotNull(id.identity());
        assertTrue(id.nullable());
        assertEquals(id.identity().seed(), 9);
        assertEquals(id.identity().increment(), 2);

        assertEquals(count.name(), "count");
        assertNotNull(count.autoIncrement());
        assertEquals(count.autoIncrement().increment(), 2);
        assertFalse(count.nullable());

        assertEquals(name.name(), "NAME");
        assertTrue(name.foreignKey());
        assertFalse(name.nullable());
        assertEquals(name.size(), 123);
    }

}