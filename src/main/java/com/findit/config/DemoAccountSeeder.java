package com.findit.config;

import com.findit.model.AppUser;
import com.findit.model.Item;
import com.findit.repo.ItemRepository;
import com.findit.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DemoAccountSeeder implements CommandLineRunner {
    private final UserRepository users;
    private final ItemRepository items;
    private final PasswordEncoder passwords;

    public DemoAccountSeeder(UserRepository users, ItemRepository items, PasswordEncoder passwords) {
        this.users = users;
        this.items = items;
        this.passwords = passwords;
    }

    @Override
    public void run(String... args) {
        createIfMissing("Campus Student", "student@findit.local", "Student@123", "Student Services", "STUDENT");
        createIfMissing("Campus Staff", "staff@findit.local", "Staff@123", "Campus Services", "STAFF");
        createIfMissing("Campus Admin", "admin@findit.local", "Admin@123", "Campus Services", "ADMIN");
        AppUser student=users.findByEmailIgnoreCase("student@findit.local").orElseThrow();
        AppUser staff=users.findByEmailIgnoreCase("staff@findit.local").orElseThrow();
        addDemoItem("Wireless headphones","Electronics","Silver over-ear headphones with a small blue sticker on the case.","LOST","Main Library, second floor","demo-headphones.svg",student,2);
        addDemoItem("Steel water bottle","Accessories","A brushed silver bottle with a teal loop lid.","LOST","Science Building, room 204","demo-bottle.svg",student,1);
        addDemoItem("Lecture notebook","Books & Notes","Dark notebook with handwritten calculus notes inside.","LOST","Lecture Hall B","demo-notebook.svg",student,3);
        addDemoItem("Set of keys","Keys & Cards","Three keys on a silver ring with a small blue tag.","FOUND","Student Union front desk","demo-keys.svg",staff,1);
        addDemoItem("Campus headphones found","Electronics","Silver headphones turned in to the campus desk. Contact staff to verify ownership.","FOUND","Campus Services desk","demo-headphones.svg",staff,0);
    }

    private void createIfMissing(String name, String email, String password, String department, String role) {
        AppUser user = users.findByEmailIgnoreCase(email).orElseGet(AppUser::new);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwords.encode(password));
        user.setDepartment(department);
        user.setRole(role);
        users.save(user);
    }

    private void addDemoItem(String name,String category,String description,String type,String location,String image,AppUser owner,int daysAgo){
        if(items.allWithOwner().stream().anyMatch(i->image.equals(i.getImageFilename())&&i.getUser().getId().equals(owner.getId())&&i.getName().equals(name)))return;
        Item item=new Item();item.setName(name);item.setCategory(category);item.setDescription(description);item.setType(type);item.setLocation(location);item.setItemDate(LocalDate.now().minusDays(daysAgo));item.setImageFilename(image);item.setUser(owner);items.save(item);
    }
}
