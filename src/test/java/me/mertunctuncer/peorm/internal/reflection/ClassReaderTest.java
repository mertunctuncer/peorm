package me.mertunctuncer.peorm.internal.reflection;

import me.mertunctuncer.peorm.mock.NamedMockTable;
import me.mertunctuncer.peorm.mock.NamelessMockTable;
import org.junit.jupiter.api.Test;

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

}