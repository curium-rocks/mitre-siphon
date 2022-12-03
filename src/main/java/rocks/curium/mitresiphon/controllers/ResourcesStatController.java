package rocks.curium.mitresiphon.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rocks.curium.mitresiphon.dao.interfaces.ResourceStatDAO;
import rocks.curium.mitresiphon.dto.ResourceStatDTO;

@RestController
@RequestMapping("api/resource-stats")
public class ResourcesStatController {

    private final ResourceStatDAO resourceStatDAO;

    public ResourcesStatController(ResourceStatDAO dao){
        resourceStatDAO = dao;
    }

    /**
     * return the resource stat information, this is primarily last access time for now
     * @param resource {String} unique resource string
     * @return {ResourceStatDTO} matched to the resource string
     */
    @GetMapping("/resource")
    public ResourceStatDTO getResourceStats(@RequestParam("url") String resource) {
        return ResourceStatDTO.fromEntity(resourceStatDAO.getResourceStat(resource));
    }
}
