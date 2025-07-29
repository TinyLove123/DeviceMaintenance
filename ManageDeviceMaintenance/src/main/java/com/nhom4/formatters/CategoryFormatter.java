///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.nhom4.formatters;
//
//import com.google.protobuf.TextFormat.ParseException;
//import com.nhom4.pojo.Category;
//import java.util.Locale;
//import org.springframework.format.Formatter;
//
///**
// *
// * @author Administrator
// */
//public class CategoryFormatter implements Formatter<Category> {
//
//    @Override
//    public String print(Category category, Locale locale) {
//        return String.valueOf(category.getId());
//    }
//
//    @Override
//    public Category parse(String cateId, Locale locale) {
//        Category c = new Category();
//        c.setId(Integer.valueOf(cateId));
//        
//        return c;
//    }
//    
//}