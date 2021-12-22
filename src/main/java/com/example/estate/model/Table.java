package com.example.estate.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Table implements Serializable {
    private final String name;
    private final String dateOfCreation;
    private String dateOfLastModification;
    private ArrayList<ArrayList<String>> table = new ArrayList<>();
    private int rows;
    private int columns;

    private final String author;

    public Table(String name, int rows, int columns, String author) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;

        this.author = author;

        this.dateOfCreation = new Date().toString();
        this.dateOfLastModification = dateOfCreation;

        for (int i = 0; i < rows; ++i) {
            table.add(new ArrayList<>());
            for (int j = 0; j < columns; ++j) {
                table.get(i).add(".");
            }
        }
    }

    public void setDateOfLastModification(String dateOfLastModification) {
        this.dateOfLastModification = dateOfLastModification;
    }

    public void addRows(int rows) {
        this.rows += rows;

        for (int i = table.size(); i < this.rows; ++i) {
            table.add(new ArrayList<>());
            for (int j = table.get(i).size(); j < this.columns; ++j) {
                table.get(i).add(".");
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public String getDateOfLastModification() {
        return dateOfLastModification;
    }

    public ArrayList<ArrayList<String>> getTable() {
        return table;
    }

    public String getAuthor() {
        return author;
    }

    public void removeRow(int row) {
        this.rows -= row;

        for (int i = table.size() - 1; i > this.rows; --i) {
            table.remove(i);
        }
    }

    public void removeColumns(int column) {
        this.columns -= column;

        for (int i = 0; i < this.rows; ++i) {
            for (int j = table.get(i).size() - 1; j > this.columns; --j) {
                table.get(i).remove(j);
            }
        }
    }

    public void addColumns(int columns) {
        this.columns += columns;

        for (int i = 0; i < this.rows; ++i) {
            for (int j = table.get(i).size(); j < this.columns; ++j) {
                table.get(i).add(".");
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setTable(int row, int column, String value) {
        for (int i = 0; i < table.size(); ++i) {
            for (int j = 0; j < table.size(); ++j) {
                if (i == row && j == column) {
                    table.get(i).set(j, value);
                }
            }
        }
    }

    public String getTableTeacher() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table width=\"100%\">");
        for (int i = 0; i < rows; ++i) {
            sb.append("<tr>");
            for (int j = 0; j < columns; ++j) {
                sb.append("<td height=\"25px\" width=\"10%\" align=\"center\">");
//                sb.append("<div id=\"editable\" contenteditable=\"true\">");
                sb.append("<a href=\"/change?name=" + name + "&row=" + i + "&column=" + j + "\">");
                sb.append(table.get(i).get(j));
                sb.append("</a>");
//                sb.append("</div>");
                sb.append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");

        sb.append("<div class=\"form_buttons\">");
        sb.append("<form action=\"view\" method=\"post\">");
        sb.append("<input type=\"hidden\" name=\"addColumn\" value=\"1\">");
        sb.append("<input type=\"submit\" value=\"addColumn\" class=\"menu_button\">");
        sb.append("</form>");
        sb.append("<form action=\"view\" method=\"post\">");
        sb.append("<input type=\"hidden\" name=\"addRow\" value=\"1\">");
        sb.append("<input type=\"submit\" value=\"addRow\" class=\"menu_button\">");
        sb.append("</form>");
        sb.append("<form action=\"view\" method=\"post\">");
        sb.append("<input type=\"hidden\" name=\"removeColumn\" value=\"1\">");
        sb.append("<input type=\"submit\" value=\"removeColumn\" class=\"menu_button\">");
        sb.append("</form>");
        sb.append("<form action=\"view\" method=\"post\">");
        sb.append("<input type=\"hidden\" name=\"removeRow\" value=\"1\">");
        sb.append("<input type=\"submit\" value=\"removeRow\" class=\"menu_button\">");
        sb.append("</form>");
        sb.append("</div>");

        return sb.toString();
    }

    public String getTableStudent() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table width=\"100%\">");
        for (int i = 0; i < rows; ++i) {
            sb.append("<tr>");
            for (int j = 0; j < columns; ++j) {
                sb.append("<td height=\"25px\" width=\"10%\" align=\"center\">");
                sb.append(table.get(i).get(j));
                sb.append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");

        return sb.toString();
    }
}
