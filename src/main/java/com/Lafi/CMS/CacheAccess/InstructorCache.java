package com.Lafi.CMS.CacheAccess;

import com.Lafi.CMS.Models.Instructors.InstructorBasic;
import com.Lafi.CMS.Models.Instructors.InstructorNull;

public class InstructorCache extends LRUCache {
    private static InstructorCache instance = null;

    public InstructorCache() {
        super();
    }

    public static synchronized InstructorCache getInstance() {
        if (instance == null) {
            instance = new InstructorCache();
        }
        return instance;
    }

    @Override
    public InstructorBasic get(long key) {
        lock.readLock().lock();
        if (hashmap.containsKey(key)) {
            Entity entity = hashmap.get(key);
            remove(entity);
            addToTop(entity);
            lock.readLock().unlock();
            return (InstructorBasic) entity.value;
        }
        lock.readLock().unlock();
        return new InstructorNull();
    }
}
