package com.motorny.models.projection;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NamedNativeQuery(
        name = "BookProjection.findBooksForUserUnderAge",
        query = """
                SELECT b.id, u.full_name, b.title, u.age
                    FROM Book b
                    INNER JOIN record r on b.id = r.book_id
                    INNER JOIN _user u ON r.user_id = u.id
                    WHERE u.age < ?1
                    GROUP BY b.id, u.full_name, b.title, u.age
                    ORDER BY u.age DESC
                    LIMIT ?2
                """,
        resultSetMapping = "BookProjectMapping"
)

@SqlResultSetMapping(
        name = "BookProjectMapping",
        entities = {
                @EntityResult(
                        entityClass = BookProjection.class,
                        fields = {
                                @FieldResult(name = "id", column = "id"),
                                @FieldResult(name = "fullName", column = "full_name"),
                                @FieldResult(name = "title", column = "title"),
                                @FieldResult(name = "age", column = "age")
                        }
                )
        }
)

@Getter
@Setter
@NoArgsConstructor
public class BookProjection {

    @Id
    private Long id;
    private String fullName;
    private String title;
    private String age;

    public BookProjection(String fullName, String title) {
        this.fullName = fullName;
        this.title = title;
    }

    public BookProjection(Long id, String fullName, String title, String age) {
        this.id = id;
        this.fullName = fullName;
        this.title = title;
        this.age = age;
    }
}
