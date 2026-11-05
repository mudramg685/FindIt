package com.findit.web;

import com.findit.model.AppUser; import com.findit.repo.UserRepository; import jakarta.servlet.http.*; import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users; private final PasswordEncoder encoder;
 public AuthController(UserRepository u,PasswordEncoder e){users=u;encoder=e;}
 private Map<String,Object> dto(AppUser u){return Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail(),"department",u.getDepartment(),"role",u.getRole());}
 @PostMapping("/register") public ResponseEntity<?> register(@RequestBody Map<String,String> b,HttpSession s){String name=clean(b.get("name")),email=clean(b.get("email")),department=clean(b.get("department")),password=b.get("password");if(name.isBlank()||department.isBlank()||email.isBlank()||password==null||password.length()<8)return ResponseEntity.badRequest().body(Map.of("error","Enter all fields and use a password with at least 8 characters."));if(users.findByEmailIgnoreCase(email).isPresent())return ResponseEntity.badRequest().body(Map.of("error","That email is already registered."));AppUser u=new AppUser();u.setName(name);u.setEmail(email.toLowerCase());u.setDepartment(department);u.setPassword(encoder.encode(password));u.setRole("STUDENT");users.save(u);s.setAttribute("uid",u.getId());return ResponseEntity.ok(dto(u));}
 @PostMapping("/login") public ResponseEntity<?> login(@RequestBody Map<String,String>b,HttpSession s){AppUser u=users.findByEmailIgnoreCase(clean(b.get("email"))).orElse(null);if(u==null||!encoder.matches(b.getOrDefault("password",""),u.getPassword()))return ResponseEntity.status(401).body(Map.of("error","Email or password is incorrect."));s.setAttribute("uid",u.getId());return ResponseEntity.ok(dto(u));}
 @PostMapping("/logout") public Map<String,String> logout(HttpSession s){s.invalidate();return Map.of("message","Logged out");}
 @GetMapping("/me") public ResponseEntity<?> me(HttpSession s){Object id=s.getAttribute("uid");if(id==null)return ResponseEntity.status(401).body(Map.of("error","Please log in."));return users.findById((Long)id).<ResponseEntity<?>>map(u->ResponseEntity.ok(dto(u))).orElseGet(()->ResponseEntity.status(401).body(Map.of("error","Please log in.")));}
 static String clean(String v){return v==null?"":v.trim();}
}
