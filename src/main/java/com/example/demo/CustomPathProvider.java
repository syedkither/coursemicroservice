package com.example.demo;

import javax.servlet.ServletContext;

import org.springframework.web.util.UriComponentsBuilder;

import io.micrometer.core.instrument.util.StringUtils;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import static springfox.documentation.spring.web.paths.Paths.*;

public class CustomPathProvider extends AbstractPathProvider {
    ServletContext servletContext;
    CustomPathProvider( ServletContext servletContext){
        this.servletContext =servletContext;
    }
    private static final String ROOT = "";
    @Override
    protected String applicationPath() {    	
        return StringUtils.isEmpty(servletContext.getContextPath()) ? ROOT : servletContext.getContextPath();
    }

    @Override
    protected String getDocumentationPath() {
        return ROOT;
    }

    @Override
    public String getOperationPath(String operationPath) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/");        
        return getOperationPathPrefix(operationPath) +
        		removeAdjacentForwardSlashes(uriComponentsBuilder.path(operationPath).build().toString());
    }

    private String getOperationPathPrefix(String path) {
        switch (path) {
            case "/student/add":
            case "/student/get":
            case "/student/remove":
                return "";   //spring boot doesn't have root context by default, if there is then add root context explicitly
            default:
                return "";
        }
    }
}
