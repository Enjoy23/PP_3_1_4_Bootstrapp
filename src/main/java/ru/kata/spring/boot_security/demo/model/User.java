package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name= "users")
public class User implements UserDetails{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String surname;
    @Column
    private int age;
    @Column
    private String name;

    @Column
    private String username;


    @Column
    private String password;

    @ManyToMany(fetch=FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;


    public User(long id, String surname, int age, String name, String username, String password, List<Role> roles) {
        this.id = id;
        this.surname = surname;
        this.age = age;
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(String surname, int age, String name, String username, String password, List<Role> roles) {
        this.surname = surname;
        this.age = age;
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public boolean isAdmin() {
        for (Role role : roles) {
            if (Objects.equals(role.getRole(), "ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    public User(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }



    @Override
    public String toString() {
        return "User{" +
                "ID = " + id +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }


}
