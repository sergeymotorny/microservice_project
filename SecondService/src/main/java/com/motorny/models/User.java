package com.motorny.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "record",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id")
    )
    private Set<Book> books = new HashSet<>();

    public void addBook(Book book) {
        this.books.add(book);
        book.getUser().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getUser().remove(this);
    }
}
