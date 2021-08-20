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

    //Controller/api to delete all the resources
    @GetMapping("/delete/resources")
    public ResponseEntity<?> deleteResources(){
        resourceService.deleteResources();
        return ResponseEntity.ok().body("All resources have been successfully deleted.");
    }

    /*

    //Controller/api to get all the projects' information by user
    @GetMapping("/getprojects/{username}")
    public ResponseEntity<?> getProjectsByUsername(@PathVariable String username){
        return ResponseEntity.ok().body(projectService.getProjectsByUsername(username));
    }


    //Controller/api to update user associated with project
    @PostMapping("/update/user/{name}/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String name, @PathVariable String username){
        User user = userService.findByUsername(username);
        Project project = projectService.findByName(name);
        if (!userService.usernameExists(user.getUsername())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username \"" + username + "\" does not exists!");
        }else if (!projectService.projectNameExists(project.getProjectName())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project \"" + project.getProjectName() + "\" does not exists!");
        }else{
            project.setUser(user);
            projectService.updateUser(name, username);
        }
        return new ResponseEntity<>(project, HttpStatus.OK);
    }


    */
}