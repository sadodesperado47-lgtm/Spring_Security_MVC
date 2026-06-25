package ru.spring.boot_security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastName")
    private String lastname;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username", unique = true)
    private String username;

    // ✅ LAZY оставлен как было в оригинале
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> roles;

    public User() {}

    public User(Integer age) {
        this.age = age;
    }

    public User(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public User(String name, String lastname, String email, Set<UserRole> roles) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.roles = roles;
    }


    public Long getId() { return id; }

    public String getName() { return name; }

    public String getFirstName() { return name; }
    public String getLastName() { return lastname; }

    public String getLastname() { return lastname; }

    public Integer getAge() { return age; }

    public String getEmail() { return email; }

    public Set<UserRole> getRoles() { return roles; }


    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }


    public void setFirstName(String firstName) { this.name = firstName; }
    public void setLastName(String lastName) { this.lastname = lastName; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public void setAge(Integer age) { this.age = age; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setUsername(String username) { this.username = username; }

    public void setRoles(Set<UserRole> roles) { this.roles = roles; }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return roles; }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}