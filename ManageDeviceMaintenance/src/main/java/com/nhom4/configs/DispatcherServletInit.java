/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.configs;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.nhom4.filters.JwtFilter;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.Filter;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;

/**
 *
 * @author admin
 */
public class DispatcherServletInit extends AbstractAnnotationConfigDispatcherServletInitializer {

//    public DispatcherServletInit() {
//        Dotenv dotenv = Dotenv.load();
//
//        System.setProperty("DB_DIALECT", dotenv.get("DB_DIALECT"));
//        System.setProperty("DB_SHOW_SQL", dotenv.get("DB_SHOW_SQL"));
//        System.setProperty("DB_URL", dotenv.get("DB_URL"));
//        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
//        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
//
//        System.out.println("✅ .env loaded successfully");
//    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
            ThymeleafConfig.class,
            HibernateConfigs.class,
            SpringSecurityConfigs.class,
             WebAppContextConfigs.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
            WebAppContextConfigs.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    
    
    
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] { new JwtFilter() }; // Filter sẽ áp dụng cho mọi request
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        String location = "/";
        long maxFileSize = 5242880; // 5MB
        long maxRequestSize = 20971520; // 20MB
        int fileSizeThreshold = 0;

        registration.setMultipartConfig(new MultipartConfigElement(location, maxFileSize, maxRequestSize, fileSizeThreshold));
    
    }
}