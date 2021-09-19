package com.Lafi.CMS.CRUDOperations;

import com.Lafi.CMS.CRUDOperations.Interfaces.*;
import com.Lafi.CMS.CacheAccess.InstructorCache;
import com.Lafi.CMS.DataBaseAccess.InstructorAccess;
import com.Lafi.CMS.Models.Instructors.InstructorBasic;

import java.util.ArrayList;


public class InstructorCRUD implements IAdd<InstructorBasic>, IDelete, IUpdate<InstructorBasic>, IReadAll<InstructorBasic>, IReadByID<InstructorBasic> {
    @Override
    public void add(InstructorBasic instructor) {
        InstructorCache cacheAccess = InstructorCache.getInstance();
        cacheAccess.put(instructor.getID(), instructor);
        InstructorAccess DBAccess = InstructorAccess.getInstance();
        DBAccess.add(instructor);
    }

    @Override
    public void delete(long ID) {
        InstructorCache cacheAccess = InstructorCache.getInstance();
        if (cacheAccess.isContains(ID))
            cacheAccess.delete(ID);
        InstructorAccess DBAccess = InstructorAccess.getInstance();
        DBAccess.delete(ID);
    }

    @Override
    public ArrayList<InstructorBasic> readAll() {
        ArrayList<InstructorBasic> results;
        InstructorAccess DBAccess = InstructorAccess.getInstance();
        results = DBAccess.readAll();
        InstructorCache cacheAccess = InstructorCache.getInstance();
        for (InstructorBasic i : results){
            cacheAccess.put(i.getID(), i);
        }
        return results;
    }

    @Override
    public InstructorBasic readByID(long ID) {
        InstructorBasic instructor;
        InstructorCache cacheAccess = InstructorCache.getInstance();
        if (cacheAccess.isContains(ID))
            instructor = cacheAccess.get(ID);
        else {
            InstructorAccess DBAccess = InstructorAccess.getInstance();
            instructor = DBAccess.readByID(ID);
            cacheAccess.put(instructor.getID(), instructor);
        }
        return instructor;
    }

    @Override
    public void update(InstructorBasic instructor) {
        InstructorCache cacheAccess = InstructorCache.getInstance();
        if (cacheAccess.isContains(instructor.getID()))
            cacheAccess.update(instructor.getID(), instructor);
        InstructorAccess DBAccess = InstructorAccess.getInstance();
        DBAccess.delete(instructor.getID());
        DBAccess.add(instructor);
    }


}
