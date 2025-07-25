/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.configs;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *
 * @author Administrator
 */
public class DispatcherServletInit extends  AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ThymeleafConfig.class,
                             HibernateConfigs.class,};
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
    
}

