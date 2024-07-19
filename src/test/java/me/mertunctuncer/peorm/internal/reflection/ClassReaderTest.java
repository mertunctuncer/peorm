package me.mertunctuncer.peorm.internal.reflection;

import me.mertunctuncer.peorm.api.util.Pair;
import me.mertunctuncer.peorm.internal.model.ColumnData;
import me.mertunctuncer.peorm.mock.NamedMockTable;
import me.mertunctuncer.peorm.mock.NamelessMockTable;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassReaderTest {

    @Test
    void reader_should_throw_npe_if_annotation_is_not_present() {
        assertThrows(NullPointerException.class, () -> ClassReader.getTableName(Object.class));
        assertDoesNotThrow(() -> ClassReader.getTableName(NamedMockTable.class));
    }

    @Test
    void table_name_should_be_read_correctly() {
        assertEquals(ClassReader.getTableName(NamedMockTable.class), "named-mock-table");
        assertEquals(ClassReader.getTableName(NamelessMockTable.class), "namelessmocktable");
    }

    @Test
    void fields_should_be_read_correctly() {
        List<Pair<Field, ColumnData>> fieldData = ClassReader.mapFields(NamedMockTable.class);

        assertEquals(3, fieldData.size());

        ColumnData id = fieldData.get(0).second();
        ColumnData count = fieldData.get(1).second();
        ColumnData name = fieldData.get(2).second();

        assertEquals(id.name(), "no");
        assertTrue(id.primaryKey());
        assertTrue(id.identity());
        assertTrue(id.nullable());
        assertEquals(id.identitySeed(), 9);
        assertEquals(id.identityIncrementAmount(), 2);

        assertEquals(count.name(), "count");
        assertTrue(count.autoIncrement());
        assertEquals(count.autoIncrementAmount(), 2);
        assertFalse(count.nullable());

        assertEquals(name.name(), "NAME");
        assertTrue(name.foreignKey());
        assertFalse(name.nullable());
        assertEquals(name.size(), 123);
    }

}