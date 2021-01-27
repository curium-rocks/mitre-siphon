package xyz.andrewkboyd.mitresiphon.dto;

import lombok.Data;
import xyz.andrewkboyd.mitresiphon.entities.ResourceStat;

import java.time.ZonedDateTime;

public @Data class ResourceStatDTO {
    private String resource;
    private ZonedDateTime lastAccessTime;

    public static ResourceStatDTO fromEntity(ResourceStat stat){
        ResourceStatDTO newObj = new ResourceStatDTO();
        if(stat == null) return newObj;
        newObj.resource = stat.getResource();
        newObj.lastAccessTime = stat.getLastAccessed();
        return newObj;
    }
}
