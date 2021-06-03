package ru.butakov.remember.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @EqualsAndHashCode.Include
    String tag;

    @ManyToMany(mappedBy = "tags")
    List<Post> posts = new ArrayList<>();

    public Tag(String tag) {
        this.tag = tag;
    }
}
