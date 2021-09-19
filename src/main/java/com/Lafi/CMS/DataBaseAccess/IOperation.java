package com.Lafi.CMS.DataBaseAccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;

public interface IOperation<T> {
    public BufferedWriter openWriter();

    public BufferedReader openReader();

    public void closeWriter(BufferedWriter oos);

    public void closeReader(BufferedReader ois);

    public void add(T value);

    public ArrayList<T> readAll();

    public T readByID(long ID);

    public void delete(long ID);
}
