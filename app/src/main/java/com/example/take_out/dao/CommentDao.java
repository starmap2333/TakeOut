package com.example.take_out.dao;

import com.example.take_out.data.Comment;

public class CommentDao {
    Comment load() {
        return new Comment();
    }

    Result upload(Comment comment) {
        return new Result();
    }

    class Result {
    }
}
