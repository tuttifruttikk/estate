package com.example.estate.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TableTest {

    @Test
    void addRows() {
        Table table = new Table("",0,0,"");
        table.addRows(4);
        Assertions.assertEquals(4, table.getRows());
    }

    @Test
    void removeRow() {
        Table table = new Table("", 4, 0, "");
        table.removeRow(2);
        Assertions.assertEquals(2, table.getRows());
    }

    @Test
    void removeColumns() {
        Table table = new Table("", 0, 4, "");
        table.removeColumns(2);
        Assertions.assertEquals(2, table.getColumns());
    }

    @Test
    void addColumns() {
        Table table = new Table("",0,0,"");
        table.addColumns(4);
        Assertions.assertEquals(4, table.getColumns());
    }

    @Test
    void setTable() {
        Table table = new Table("", 1, 1, "");
        table.setTable(0, 0, "Hello");
        String res = table.getTable().get(0).get(0);
        Assertions.assertEquals("Hello", res);
    }
}