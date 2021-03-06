package com.itlize.korera.service;

import com.itlize.korera.model.ProjectResource;
import com.itlize.korera.model.Resource;

import java.util.List;

public interface ResourceService {
    boolean resourceNameExists(String name);
    boolean resourceCodeExists(String code);
    boolean resourceIdExists(Integer id);
    Resource saveResource(Resource resource);
    ProjectResource addResourceToProject(String resourceName, String projectName);
    Resource findByName(String name);
    Resource findByCode(String code);
    Resource findById(Integer id);
    List<ProjectResource> getResourcesByProject(String projectName);
    List<ProjectResource> getProjectsByResource(String resourceName);
    List<Resource> getResources();
    Resource updateName(Resource resource, String name);
    Resource updateCode(Resource resource, String code);
    ProjectResource updateProject(Integer projectResourceId, String projectName);
    ProjectResource updateResource(Integer projectResourceId, String projectName);
    ProjectResource removeProject(Integer projectResourceId, String projectName);
    ProjectResource removeResource(Integer projectResourceId, String resourceName);
    void deleteByName(String name);
    void deleteByCode(String code);
    void deleteById(Integer id);
    void deleteResources();
}
