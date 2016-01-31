package com.runeventsparser.bom;

/**
 * Created by Николай on 31.01.2016.
 */
public class Role extends EntityWithName{

    @Override
    public String toString() {
        return "[" + getId() + ","+getName()+"]";
    }
}

