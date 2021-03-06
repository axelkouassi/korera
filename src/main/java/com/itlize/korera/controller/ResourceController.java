package com.itlize.korera.controller;

import com.itlize.korera.model.Project;
import com.itlize.korera.model.ProjectResource;
import com.itlize.korera.model.Resource;
import com.itlize.korera.model.User;
import com.itlize.korera.service.ProjectResourceService;
import com.itlize.korera.service.ProjectService;
import com.itlize.korera.service.ResourceService;
import com.itlize.korera.service.UserService;
import com.itlize.korera.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ProjectResourceService projectResourceService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    //Controller/api to create a resource
    @PostMapping("/create")
    public ResponseEntity<?> createResource(@RequestBody Resource resource){
        resourceService.saveResource(resource);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    //Controller/api to add a resource to a project
    @PostMapping("/add/project/{resourcename}/{projectname}")
    public ResponseEntity<?> addResourceToProject(@PathVariable String resourcename
            , @PathVariable String projectname) {
        return new ResponseEntity<>(resourceService.addResourceToProject(resourcename, projectname)
                , HttpStatus.OK);
    }

    //Controller/api to get resource information based on name
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name){
        if (!resourceService.resourceNameExists(name)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource \""
                    + resourceService.findByName(name).getResourceName()
                    + "\" does not exists!");
        }
        return ResponseEntity.ok().body(resourceService.findByName(name));
    }

    //Controller/api to get resource information based on code
    @GetMapping("/code/{code}")
    public ResponseEntity<?> getByCode(@PathVariable String code){
        if (!resourceService.resourceCodeExists(code)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource \""
                    + resourceService.findByCode(code).getResourceCode()
                    + "\" does not exists!");
        }
        return ResponseEntity.ok().body(resourceService.findByCode(code));
    }

    //Controller/api to get project information based on project id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        if (!resourceService.resourceIdExists(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource \""
                    + resourceService.findById(id).getResourceId()
                    + "\" does not exists!");
        }
        return ResponseEntity.ok().body(resourceService.findById(id));
    }

    //Controller/api to get a list of resources used by a particular project
    @GetMapping("/resources/project/{projectname}")
    public ResponseEntity<?> getResourcesByProject(@PathVariable String projectname) {
        return new ResponseEntity<>(resourceService.getResourcesByProject(projectname)
                , HttpStatus.OK);
    }

    //Controller/api to get a list of projects based on resource
    @GetMapping("/projects/resource/{resourcename}")
    public ResponseEntity<?> getProjectsByResource(@PathVariable String resourcename) {
        return new ResponseEntity<>(resourceService.getProjectsByResource(resourcename)
                , HttpStatus.OK);
    }

    //Controller/api to get all the resources' information
    @GetMapping("/getresources")
    public ResponseEntity<?> getResources(){
        return ResponseEntity.ok().body(resourceService.getResources());
    }

    //Controller/api to update resource name
    @PostMapping("/update/name/{name}/{newname}")
    public ResponseEntity<?> updateName(@PathVariable String name, @PathVariable String newname){
        Resource resource = resourceService.findByName(name);
        if (!resourceService.resourceNameExists(name)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource \"" + resource.getResourceName()
                    + "\" does not exists!");
        }
        resourceService.updateName(resource,newname);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    //Controller/api to update resource code
    @PostMapping("/update/code/{code}/{newcode}")
    public ResponseEntity<?> updateCode(@PathVariable String code, @PathVariable String newcode){
        Resource resource = resourceService.findByCode(code);
        if (!resourceService.resourceCodeExists(code)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource \"" + resource.getResourceCode()
                    + "\" does not exists!");
        }
        resourceService.updateCode(resource,newcode);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    //Controller/api to update project associated with a resource
    @PostMapping("/update/project/{projectresourceid}/{projectname}")
    public ResponseEntity<?> updateProject(@PathVariable Integer projectresourceid, @PathVariable String projectname) {
        ProjectResource projectResource = projectResourceService.findById(projectresourceid);
        resourceService.updateProject(projectresourceid,projectname);
        return new ResponseEntity<>("Project with name " + projectname +
                " has now been assigned to resource with name "
                + projectResource.getResource().getResourceName(), HttpStatus.OK);
    }

    //Controller/api to update resource associated with a project
    @PostMapping("/update/resource/{projectresourceid}/{resourcename}")
    public ResponseEntity<?> updateResource(@PathVariable Integer projectresourceid, @PathVariable String resourcename) {
        ProjectResource projectResource = projectResourceService.findById(projectresourceid);
        resourceService.updateResource(projectresourceid,resourcename);
        return new ResponseEntity<>("Resource with name " + resourcename +
                " has now been assigned to project with name "
                + projectResource.getProject().getProjectName(), HttpStatus.OK);
    }

    //Controller/api to delete resource based on name
    @GetMapping("/delete/name/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable String name){
        if (!resourceService.resourceNameExists(name)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource \"" + name + "\" does not exists!");
        }
        resourceService.deleteByName(name);
        return ResponseEntity.ok().body("Resource \"" + name + "\" was successfully deleted.");
    }

    //Controller/api to delete resource based on code
    @GetMapping("/delete/code/{code}")
    public ResponseEntity<?> deleteByCode(@PathVariable String code){
        if (!resourceService.resourceCodeExists(code)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource \"" + code + "\" does not exists!");
        }
        resourceService.deleteByCode(code);
        return ResponseEntity.ok().body("Resource \"" + code + "\" was successfully deleted.");
    }

    //Controller/api to delete resource by id
    @GetMapping("/delete/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        if (!resourceService.resourceIdExists(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource with id \"" + id + "\" does not exists!");
        }
        resourceService.deleteById(id);
        return ResponseEntity.ok().body("Resource with id \"" + id + "\" was successfully deleted.");
    }

    //Controller/api to remove project associated with a resource
    @PostMapping("/remove/project/{projectresourceid}/{projectname}")
    public ResponseEntity<?> removeProject(@PathVariable Integer projectresourceid, @PathVariable String projectname) {
        ProjectResource projectResource = projectResourceService.findById(projectresourceid);
        resourceService.removeProject(projectresourceid,projectname);
        return new ResponseEntity<>("Project with name " + projectname +
                " is no longer associated with resource with name "
                + projectResource.getResource().getResourceName(), HttpStatus.OK);
    }

    //Controller/api to remove resource associated with a project
    @PostMapping("/remove/resource/{projectresourceid}/{resourcename}")
    public ResponseEntity<?> removeResource(@PathVariable Integer projectresourceid, @PathVariable String resourcename) {
        ProjectResource projectResource = projectResourceService.findById(projectresourceid);
        resourceService.removeResource(projectresourceid,resourcename);
        return new ResponseEntity<>("Resource with name " + resourcename +
                " is no longer associated with project with name "
                + projectResource.getProject().getProjectName(), HttpStatus.OK);
    }

    //Controller/api to delete all the resources
    @GetMapping("/delete/resources")
    public ResponseEntity<?> deleteResources(){
        resourceService.deleteResources();
        return ResponseEntity.ok().body("All resources have been successfully deleted.");
    }
}
