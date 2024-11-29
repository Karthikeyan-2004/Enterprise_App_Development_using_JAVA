/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example;

/**
 *
 * @author karth
 */
import jakarta.ejb.Stateless;
@Stateless
public class LoginBean {
 public boolean authenticate(String username, String password) {
 // In a real application, you would check against a database
 return "admin".equals(username) && "password".equals(password);
 }
}
