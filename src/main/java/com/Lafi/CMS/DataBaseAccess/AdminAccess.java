package com.Lafi.CMS.DataBaseAccess;

import com.Lafi.CMS.Models.Admin;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AdminAccess implements IOperation<Admin> {

    static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static AdminAccess instance = null;

    public AdminAccess() {
        super();
    }

    public static synchronized AdminAccess getInstance() {
        if (instance == null) {
            instance = new AdminAccess();
        }
        return instance;
    }

    private static ObjectMapper mapper() {
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        ObjectMapper mapper = new ObjectMapper(jsonFactory);
        return mapper;
    }

    @Override
    public BufferedWriter openWriter() {
        lock.writeLock().lock();
        FileWriter fileWriter;
        BufferedWriter writer = null;
        try {
            fileWriter = new FileWriter("src/main/java/com/Lafi/Tables/Admins.json", true);
            writer = new BufferedWriter(fileWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer;
    }

    @Override
    public BufferedReader openReader() {
        lock.readLock().lock();
        FileReader fileReader;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader("src/main/java/com/Lafi/Tables/Admins.json");
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedReader;
    }

    @Override
    public void closeWriter(BufferedWriter writer) {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lock.writeLock().unlock();
    }

    @Override
    public void closeReader(BufferedReader reader) {
        try {

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lock.readLock().unlock();
    }


    @Override
    public void add(Admin admin) {
        BufferedWriter writer = openWriter();
        new UserAccess().add(admin);
        try {
            writer.write("\n");
            mapper().writeValue(writer, admin);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWriter(writer);
        }
    }

    @Override
    public ArrayList readAll() {
        ArrayList<Admin> results = new ArrayList<>();
        BufferedReader reader = openReader();
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                results.add(mapper().readValue(currentLine, Admin.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return results;
    }

    @Override
    public Admin readByID(long ID) {
        Admin temp;
        Admin admin = new Admin(true);

        BufferedReader reader = openReader();
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                temp = mapper().readValue(currentLine, Admin.class);
                if (temp.getID() == ID) {
                    admin = temp;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return admin;
    }

    public long getLastID() {
        BufferedReader reader = openReader();
        long lastID = 4000;
        Admin temp;
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                temp = mapper().readValue(currentLine, Admin.class);
                if (temp.getID() > lastID)
                    lastID = temp.getID();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return lastID;
    }

    @Override
    public void delete(long ID) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = openReader();
            FileWriter tempFile = new FileWriter("src/main/java/com/Lafi/Tables/Admins_temp.json");
            writer = new BufferedWriter(tempFile);
            Admin admin;
            String currentLine;
            boolean checkFirstLine = true;
            while ((currentLine = reader.readLine()) != null) {
                admin = mapper().readValue(currentLine, Admin.class);
                if (admin.getID() == ID) continue;
                if (checkFirstLine){
                    mapper().writeValue(writer, admin);
                    checkFirstLine = false;
                    continue;
                }
                writer.write("\n");
                mapper().writeValue(writer, admin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert reader != null;
            closeReader(reader);
            assert writer != null;
            closeWriter(writer);
            moveFile();
        }
    }

    private void moveFile() {
        Path source = Paths.get("src/main/java/com/Lafi/Tables/Admins_temp.json");
        Path target = Paths.get("src/main/java/com/Lafi/Tables/Admins.json");

        try {
            Files.delete(target);
            Files.move(source, target);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
