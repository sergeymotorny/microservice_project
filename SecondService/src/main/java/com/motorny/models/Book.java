package com.motorny.models;

import com.motorny.models.projection.PopularBookView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedNativeQuery(
        name = "PopularBookView.findBooksForUserUnderAge",
        query = """
                SELECT u.full_name fullName, b.title, u.age
                    FROM Book b
                    INNER JOIN record r on b.id = r.book_id
                    INNER JOIN _user u ON r.user_id = u.id
                    WHERE u.age < ?1
                    GROUP BY u.full_name, b.title, u.age
                    ORDER BY u.age DESC
                    LIMIT ?2
                """,
        resultSetMapping = "BookProjectMapping"
)

@SqlResultSetMapping(
        name = "BookProjectMapping",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "fullName", type = String.class),
                                @ColumnResult(name = "title", type = String.class),
                                @ColumnResult(name = "age", type = Integer.class)
                        },
                        targetClass = PopularBookView.class
                )
        }
)

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "pages", nullable = false)
    private Integer pages;

    @ManyToMany(mappedBy = "books")
    @Fetch(FetchMode.SUBSELECT)
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        this.users.add(user);
        user.getBooks().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getBooks().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id)
                && Objects.equals(title, book.title)
                && Objects.equals(pages, book.pages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, pages);
    }
}
