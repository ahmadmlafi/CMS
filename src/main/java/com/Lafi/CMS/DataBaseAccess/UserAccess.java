package com.Lafi.CMS.DataBaseAccess;

import com.Lafi.CMS.Models.User;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class UserAccess implements IOperation<User> {
    static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

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
            fileWriter = new FileWriter("src/main/java/com/Lafi/Tables/Users.json", true);
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
            fileReader = new FileReader("src/main/java/com/Lafi/Tables/Users.json");
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
    public void add(User user) {
        BufferedWriter writer = openWriter();
        try {
            writer.write("\n");
            mapper().writeValue(writer,user);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWriter(writer);
        }
    }

    @Override
    public ArrayList<User> readAll() {
        ArrayList<User> results = new ArrayList<>();
        BufferedReader reader = openReader();
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                results.add(mapper().readValue(currentLine, User.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return results;
    }

    @Override
    public User readByID(long ID) {
        User temp;
        User user = new User(true);

        BufferedReader reader = openReader();
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                temp = mapper().readValue(currentLine, User.class);
                if (temp.getID() == ID) {
                    user = temp;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return user;
    }

    public User readByEmail(String email) {
        User temp;
        User user = new User(true);

        BufferedReader reader = openReader();
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                temp = mapper().readValue(currentLine, User.class);
                if (temp.getEmail().equalsIgnoreCase(email)) {
                    user = temp;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return user;
    }


    @Override
    public void delete(long ID) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = openReader();
            FileWriter tempFile = new FileWriter("src/main/java/com/Lafi/Tables/Users_temp.json");
            writer = new BufferedWriter(tempFile);
            User user;
            String currentLine;
            boolean checkFirstLine = true;
            while ((currentLine = reader.readLine()) != null) {
                user = mapper().readValue(currentLine, User.class);
                if (user.getID() == ID) continue;
                if (checkFirstLine){
                    mapper().writeValue(writer, user);
                    checkFirstLine = false;
                    continue;
                }
                writer.write("\n");
                mapper().writeValue(writer, user);
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
        Path source = Paths.get("src/main/java/com/Lafi/Tables/Users_temp.json");
        Path target = Paths.get("src/main/java/com/Lafi/Tables/Users.json");

        try {
            Files.delete(target);
            Files.move(source, target);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

