package com.kors.parser.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String login;
    private String password;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
    @Column(unique = true)
    private String mail;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "user_publications", joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "publication_id") } )
    private List<Publication> favorites = new ArrayList<>();
    private Boolean subscribed = false;

    public UserEntity() {
    }

    public UserEntity(Long id, String login, String password, Role role, String mail, List<Publication> favorites, Boolean subscribed) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.mail = mail;
        this.favorites = favorites;
        this.subscribed = subscribed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Publication> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Publication> favorites) {
        this.favorites = favorites;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }
}
