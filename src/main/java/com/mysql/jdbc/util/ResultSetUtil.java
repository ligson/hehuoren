package com.mysql.jdbc.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetUtil
{
  public static String resultSet_1 = "921933822CFD27815DDBE4FCBE2B10E0";
  public static String resultSet_2 = "CE277ED4DF3ABBCBDD52B5CCF69B45BC";
  public static String resultSet_3 = "EE8374EC4E4AD797D42350C904D73077";
  public static String resultSet_4 = "FD6BEF338D81F0E62D38CEBEE09FDAF2";

  public static StringBuffer appendResultSetSlashGStyle(StringBuffer appendTo, ResultSet rs)
    throws SQLException
  {
    ResultSetMetaData rsmd = rs.getMetaData();

    int numFields = rsmd.getColumnCount();
    int maxWidth = 0;

    String[] fieldNames = new String[numFields];

    for (int i = 0; i < numFields; i++) {
      fieldNames[i] = rsmd.getColumnLabel(i + 1);

      if (fieldNames[i].length() > maxWidth) {
        maxWidth = fieldNames[i].length();
      }
    }

    int rowCount = 1;

    while (rs.next()) {
      appendTo.append("*************************** ");
      appendTo.append(rowCount++);
      appendTo.append(". row ***************************\n");

      for (int i = 0; i < numFields; i++) {
        int leftPad = maxWidth - fieldNames[i].length();

        for (int j = 0; j < leftPad; j++) {
          appendTo.append(" ");
        }

        appendTo.append(fieldNames[i]);
        appendTo.append(": ");

        String stringVal = rs.getString(i + 1);

        if (stringVal != null)
          appendTo.append(stringVal);
        else {
          appendTo.append("NULL");
        }

        appendTo.append("\n");
      }

      appendTo.append("\n");
    }

    return appendTo;
  }
}