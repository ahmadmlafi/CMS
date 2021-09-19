package com.Lafi.CMS.DataBaseAccess;

import com.Lafi.CMS.Models.Take;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TakeAccess implements IOperation<Take> {
    static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static TakeAccess instance = null;

    public TakeAccess() {
        super();
    }

    public static synchronized TakeAccess getInstance() {
        if (instance == null) {
            instance = new TakeAccess();
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
            fileWriter = new FileWriter("src/main/java/com/Lafi/Tables/Takes.json", true);
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
            fileReader = new FileReader("src/main/java/com/Lafi/Tables/Takes.json");
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
    public void add(Take take) {
        BufferedWriter writer = openWriter();
        try {
            writer.write("\n");
            mapper().writeValue(writer, take);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWriter(writer);
        }
    }


    @Override
    public ArrayList<Take> readAll() {
        ArrayList<Take> results = new ArrayList<>();
        BufferedReader reader = openReader();
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                results.add(mapper().readValue(currentLine, Take.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return results;
    }

    @Override
    public Take readByID(long ID) {
        return new Take(true);
    }


    public Take readGrade(long student_id, long course_id) {
        Take temp;
        Take take = new Take(true);
        BufferedReader reader = openReader();
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                temp = mapper().readValue(currentLine, Take.class);
                if (temp.getCourse_ID() == course_id & temp.getStudent_ID() == student_id) {
                    take = temp;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return take;
    }

    public ArrayList<Take> readByStudentID(long ID) {
        ArrayList<Take> results = new ArrayList<>();
        Take temp;
        BufferedReader reader = openReader();
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                temp = mapper().readValue(currentLine, Take.class);
                if (temp.getStudent_ID() == ID) {
                    results.add(temp);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return results;
    }

    public ArrayList<Take> readByCourseID(long ID) {
        ArrayList<Take> results = new ArrayList<>();
        Take temp;
        BufferedReader reader = openReader();
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                temp = mapper().readValue(currentLine, Take.class);
                if (temp.getCourse_ID() == ID) {
                    results.add(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return results;
    }

    @Override
    public void delete(long ID) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = openReader();
            FileWriter tempFile = new FileWriter("src/main/java/com/Lafi/Tables/Takes_temp.json");
            writer = new BufferedWriter(tempFile);
            Take take;
            String currentLine;
            boolean checkFirstLine = true;
            while ((currentLine = reader.readLine()) != null) {
                take = mapper().readValue(currentLine, Take.class);
                if (take.getCourse_ID() == ID) continue;
                if (checkFirstLine) {
                    mapper().writeValue(writer, take);
                    checkFirstLine = false;
                    continue;
                }
                writer.write("\n");
                mapper().writeValue(writer, take);
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


    public void deleteAllWithStudentID(long ID) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = openReader();
            FileWriter tempFile = new FileWriter("src/main/java/com/Lafi/Tables/Takes_temp.json");
            writer = new BufferedWriter(tempFile);
            Take take;
            String currentLine;
            boolean checkFirstLine = true;
            while ((currentLine = reader.readLine()) != null) {
                take = mapper().readValue(currentLine, Take.class);
                if (take.getStudent_ID() == ID) continue;
                if (checkFirstLine) {
                    mapper().writeValue(writer, take);
                    checkFirstLine = false;
                    continue;
                }
                writer.write("\n");
                mapper().writeValue(writer, take);
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
        Path source = Paths.get("src/main/java/com/Lafi/Tables/Takes_temp.json");
        Path target = Paths.get("src/main/java/com/Lafi/Tables/Takes.json");

        try {
            Files.delete(target);
            Files.move(source, target);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteWithStudentAndCourse(long student_ID, long course_ID) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = openReader();
            FileWriter tempFile = new FileWriter("src/main/java/com/Lafi/Tables/Takes_temp.json");
            writer = new BufferedWriter(tempFile);
            Take take;
            String currentLine;
            boolean checkFirstLine = true;
            while ((currentLine = reader.readLine()) != null) {
                take = mapper().readValue(currentLine, Take.class);
                if (take.getCourse_ID() == course_ID & take.getStudent_ID() == student_ID) continue;
                if (checkFirstLine) {
                    mapper().writeValue(writer, take);
                    checkFirstLine = false;
                    continue;
                }
                writer.write("\n");
                mapper().writeValue(writer, take);
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
}
