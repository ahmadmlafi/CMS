package com.Lafi.CMS.CacheAccess;

import com.Lafi.CMS.Models.Courses.CourseBasic;
import com.Lafi.CMS.Models.Courses.CourseNull;

public class CourseCache extends LRUCache{
    private static CourseCache instance = null;

    public CourseCache() {
        super();
    }

    public static synchronized CourseCache getInstance() {
        if (instance == null) {
            instance = new CourseCache();
        }
        return instance;
    }

    @Override
    public CourseBasic get(long key) {
        lock.readLock().lock();
        if (hashmap.containsKey(key)) {
            Entity entity = hashmap.get(key);
            remove(entity);
            addToTop(entity);
            lock.readLock().unlock();
            return (CourseBasic) entity.value;
        }
        lock.readLock().unlock();
        return new CourseNull();
    }
}
