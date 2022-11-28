package com.project.bloggy.repository;

import com.project.bloggy.entity.Label;
import com.project.bloggy.entity.Post;
import com.project.bloggy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);
    List<Post> findByLabel(Label label);
}
