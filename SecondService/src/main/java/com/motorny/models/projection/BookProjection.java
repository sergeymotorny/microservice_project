package com.motorny.models.projection;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NamedNativeQuery(
        name = "BookProjection.findBooksForUserUnderAge",
        query = """
                SELECT u.full_name, b.title, u.age
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
                        targetClass = BookProjection.class
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

    public BookProjection(String fullName, String title, String age) {
        this.fullName = fullName;
        this.title = title;
        this.age = age;
    }
}
