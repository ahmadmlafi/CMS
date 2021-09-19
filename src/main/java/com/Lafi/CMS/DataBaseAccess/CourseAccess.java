package com.Lafi.CMS.DataBaseAccess;

import com.Lafi.CMS.Models.Courses.CourseBasic;
import com.Lafi.CMS.Models.Courses.CourseNull;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CourseAccess implements IOperation<CourseBasic> {
    static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public CourseAccess() {
        super();
    }

    private static ObjectMapper mapper() {
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        ObjectMapper mapper = new ObjectMapper(jsonFactory);
        return mapper;
    }
    private static CourseAccess instance = null;

    public static synchronized CourseAccess getInstance() {
        if (instance == null) {
            instance = new CourseAccess();
        }
        return instance;
    }

    @Override
    public BufferedWriter openWriter() {
        lock.writeLock().lock();
        FileWriter fileWriter;
        BufferedWriter writer = null;
        try {
            fileWriter = new FileWriter("src/main/java/com/Lafi/Tables/Courses.json", true);
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
            fileReader = new FileReader("src/main/java/com/Lafi/Tables/Courses.json");
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

    public long getLastID() {
        BufferedReader reader = openReader();
        long lastID = 3000;
        CourseBasic temp;
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                temp = mapper().readValue(currentLine, CourseBasic.class);
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
    public void add(CourseBasic course) {
        BufferedWriter writer = openWriter();
        try {
            writer.write("\n");
            mapper().writeValue(writer, course);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWriter(writer);
        }
    }


    @Override
    public ArrayList<CourseBasic> readAll() {
        ArrayList<CourseBasic> results = new ArrayList<>();
        BufferedReader reader = openReader();
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                results.add(mapper().readValue(currentLine, CourseBasic.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return results;
    }


    @Override
    public CourseBasic readByID(long ID) {
        CourseBasic temp;
        CourseBasic course = new CourseNull();

        BufferedReader reader = openReader();
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                temp = mapper().readValue(currentLine, CourseBasic.class);
                if (temp.getID() == ID) {
                    course = temp;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return course;
    }

    public ArrayList<Long> getCoursesIDForInstructor(long instructor_id) {
        ArrayList<Long> results = new ArrayList<>();
        BufferedReader reader = openReader();
        CourseBasic temp;
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                temp = mapper().readValue(currentLine, CourseBasic.class);
                if (temp.getInstructor_ID() == instructor_id) {
                    results.add(temp.getID());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
        return results;
    }

    public ArrayList<CourseBasic> getCoursesForInstructor(long instructor_id) {
        ArrayList<Long> coursesID = getCoursesIDForInstructor(instructor_id);
        ArrayList<CourseBasic> results = new ArrayList<>();
        BufferedReader reader = openReader();
        CourseBasic temp;
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                temp = mapper().readValue(currentLine, CourseBasic.class);
                if (coursesID.contains(temp.getID())) {
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

    public void setCoursesInstructorToUndefined(long instructor_id) {
        ArrayList<Long> coursesID = getCoursesIDForInstructor(instructor_id);
        BufferedReader reader = openReader();
        CourseBasic temp;
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                temp = mapper().readValue(currentLine, CourseBasic.class);
                if (coursesID.contains(temp.getID())) {
                    temp.setInstructor_ID(-1);
                    delete(temp.getID());
                    add(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }
    }

    @Override
    public void delete(long ID) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = openReader();
            FileWriter tempFile = new FileWriter("src/main/java/com/Lafi/Tables/Courses_temp.json");
            writer = new BufferedWriter(tempFile);
            CourseBasic course;
            String currentLine;
            boolean checkFirstLine = true;
            while ((currentLine = reader.readLine()) != null) {
                course = mapper().readValue(currentLine, CourseBasic.class);
                if (course.getID() == ID) continue;
                if (checkFirstLine) {
                    mapper().writeValue(writer, course);
                    checkFirstLine = false;
                    continue;
                }
                writer.write("\n");
                mapper().writeValue(writer, course);
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
        Path source = Paths.get("src/main/java/com/Lafi/Tables/Courses_temp.json");
        Path target = Paths.get("src/main/java/com/Lafi/Tables/Courses.json");

        try {
            Files.delete(target);
            Files.move(source, target);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
