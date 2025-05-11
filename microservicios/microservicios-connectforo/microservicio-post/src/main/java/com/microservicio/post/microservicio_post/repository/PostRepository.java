package com.microservicio.post.microservicio_post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.post.microservicio_post.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
