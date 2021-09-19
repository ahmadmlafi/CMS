package com.Lafi.CMS.DataBaseAccess;

import com.Lafi.CMS.Models.Instructors.InstructorBasic;
import com.Lafi.CMS.Models.Instructors.InstructorNull;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InstructorAccess implements IOperation<InstructorBasic> {
    static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static InstructorAccess instance = null;

    public InstructorAccess() {
        super();
    }

    private static ObjectMapper mapper() {
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        ObjectMapper mapper = new ObjectMapper(jsonFactory);
        return mapper;
    }

    public static synchronized InstructorAccess getInstance() {
        if (instance == null) {
            instance = new InstructorAccess();
        }
        return instance;
    }

    public BufferedWriter openWriter() {
        lock.writeLock().lock();
        FileWriter fileWriter;
        BufferedWriter writer = null;
        try {
            fileWriter = new FileWriter("src/main/java/com/Lafi/Tables/Instructors.json", true);
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
            fileReader = new FileReader("src/main/java/com/Lafi/Tables/Instructors.json");
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
    public void add(InstructorBasic instructor) {
        BufferedWriter writer = openWriter();
        new UserAccess().add(instructor);
        try {
            writer.write("\n");
            mapper().writeValue(writer, instructor);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWriter(writer);
        }
    }

    @Override
    public ArrayList<InstructorBasic> readAll() {
        ArrayList<InstructorBasic> results = new ArrayList<>();
        BufferedReader reader = openReader();
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                results.add(mapper().readValue(currentLine, InstructorBasic.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return results;
    }


    @Override
    public InstructorBasic readByID(long ID) {
        InstructorBasic temp;
        InstructorBasic instructor = new InstructorNull();

        BufferedReader reader = openReader();
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                temp = mapper().readValue(currentLine, InstructorBasic.class);
                if (temp.getID() == ID) {
                    instructor = temp;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return instructor;
    }

    public long getLastID() {
        BufferedReader reader = openReader();
        long lastID = 2000;
        InstructorBasic temp;
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                temp = mapper().readValue(currentLine, InstructorBasic.class);
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
            FileWriter tempFile = new FileWriter("src/main/java/com/Lafi/Tables/Instructors_temp.json");
            writer = new BufferedWriter(tempFile);
            InstructorBasic instructor;
            String currentLine;
            boolean checkFirstLine = true;
            while ((currentLine = reader.readLine()) != null) {
                instructor = mapper().readValue(currentLine, InstructorBasic.class);
                if (instructor.getID() == ID) continue;
                if (checkFirstLine){
                    mapper().writeValue(writer, instructor);
                    checkFirstLine = false;
                    continue;
                }
                writer.write("\n");
                mapper().writeValue(writer, instructor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert reader != null;
            closeReader(reader);
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            moveFile();
        }
    }

    private void moveFile() {
        Path source = Paths.get("src/main/java/com/Lafi/Tables/Instructors_temp.json");
        Path target = Paths.get("src/main/java/com/Lafi/Tables/Instructors.json");

        try {
            Files.delete(target);
            Files.move(source, target);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
