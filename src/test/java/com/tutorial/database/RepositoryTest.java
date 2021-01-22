package com.tutorial.database;

import com.tutorial.database.entity.Comment;
import com.tutorial.database.repository.CommentRepository;
import com.tutorial.database.repository.CommentRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RepositoryTest {

    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        commentRepository = new CommentRepositoryImpl();
    }

    @Test
    void testInsert() {
        Comment comment = new Comment("ibad@test.com", "hi");
        commentRepository.insert(comment);

        Assertions.assertNotNull(comment.getId());
        System.out.println(comment.getId());
    }

    @Test
    void testFindByid() {
        Comment comment = commentRepository.findById(2205); // sesuaikan parameternya dengan ID yang ada di database
        Assertions.assertNotNull(comment);
        System.out.println(comment.getId());
        System.out.println(comment.getEmail());
        System.out.println(comment.getComment());

        Comment commentNotFound = commentRepository.findById(1_000_000);
        Assertions.assertNull(commentNotFound);
    }

    @Test
    void testFindAll() {
        List<Comment> comments = commentRepository.findAll();
        System.out.println(comments.size());
    }

    @Test
    void testFindByEmail() {
        List<Comment> comments = commentRepository.findAllByEmail("ibad@test.com");
        System.out.println(comments.size());
    }
}

/*
*
* NOTE :
*
* Secara garis besar, konsep Repository dan DAO (Data Access Object) hampir sama,
  yang membedakan repository tidak menganggap bahwa storage itu hanya database, sedangkan biasanya kalo di DAO storagenya berupa database.
* Jadi untuk kasus database, sebenarnya DAO lebih cocok, namun karena sekarang lebih populer nama Repository Pattern.
* Repository dan DAO (Data Access Object) tujuannya sama yaitu untuk mengenkapsulasi cara mengakses data.
* Entity/Model adalah representasi dari sebuah tabel di database.
*
* */