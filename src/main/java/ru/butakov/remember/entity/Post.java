package ru.butakov.remember.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotBlank(message = "Text cannot be empty")
    @Size(max = 2048, message = "Post is too long (more than 2kB)")
    String text;
    @NotBlank(message = "Tag cannot be empty")
//    @Size(max = 64, message = "Tag is too long (more than 64 symbols)")
    @Transient
    String tag;
    String filename;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User author;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "post_tag",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    Set<Tag> tags = new HashSet<>();


    public Post(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", tag='" + tag + '\'' +
                ", filename='" + filename + '\'' +
                ", author=" + author +
                '}';
    }
}
