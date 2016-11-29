/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.tools;

import java.io.*;
import java.awt.Point;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author fg79cj
 */
public class CSV {

    private HashMap<Point, String> _map = new HashMap<Point, String>();
    private int _cols;
    private int _rows;

    public void open(File file) throws FileNotFoundException, IOException {
        open(file, ',');
    }

    public void open(File file, char delimiter)
            throws FileNotFoundException, IOException {
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(Character.toString(delimiter));

        clear();

        while (scanner.hasNextLine()) {
            String[] values = scanner.nextLine().split(Character.toString(delimiter));

            int col = 0;
            for (String value : values) {
                _map.put(new Point(col, _rows), value);
                _cols = Math.max(_cols, ++col);
            }
            _rows++;
        }
        scanner.close();
    }

    public void save(File file) throws IOException {
//        System.out.println("save phase 1 +++++++++++++++++++++++++++++++");
        save(file, ',');
//        System.out.println("save phase 2 ++++++++++++++++++++++++++++++");
    }

    public void save(File file, char delimiter) throws IOException {
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        for (int row = 0; row < _rows; row++) {
            for (int col = 0; col < _cols; col++) {
                Point key = new Point(col, row);
                if (_map.containsKey(key)) {
                    bw.write(_map.get(key));
                }

                if ((col + 1) < _cols) {
                    bw.write(delimiter);
                }
            }
            if( row != _rows -1) {
                bw.newLine();
            }
        }
        bw.flush();
        bw.close();
    }

    public String get(int col, int row) {
        String val = "";
        Point key = new Point(col, row);
        if (_map.containsKey(key)) {
            val = _map.get(key);
        }
        return val;
    }

    public void put(int col, int row, String value) {
        _map.put(new Point(col, row), value);
        _cols = Math.max(_cols, col + 1);
        _rows = Math.max(_rows, row + 1);
    }

    public void clear() {
        _map.clear();
        _cols = 0;
        _rows = 0;
    }

    public int rows() {
        return _rows;
    }

    public int cols() {
        return _cols;
    }

}
