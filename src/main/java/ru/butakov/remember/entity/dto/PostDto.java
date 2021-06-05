package ru.butakov.remember.entity.dto;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.butakov.remember.entity.Post;
import ru.butakov.remember.entity.Tag;
import ru.butakov.remember.entity.User;

import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class PostDto {
    @EqualsAndHashCode.Include
    long id;
    String text;
    String tag;
    Set<Tag> tags;
    String filename;
    User author;
    int likes;
    boolean liked;
    boolean myPost;

    public PostDto(Post post, User currentUser) {
        id = post.getId();
        text = post.getText();
        tag = post.getTag();
        tags = post.getTags();
        filename = post.getFilename();
        author = post.getAuthor();
        likes = post.getLikes().size();
        liked = post.getLikes().contains(currentUser);
        myPost = post.getAuthor().equals(currentUser);
    }
}
