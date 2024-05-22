package com.example.baddit.domain.repository

import com.example.baddit.domain.error.DataError
import com.example.baddit.domain.error.Result
import com.example.baddit.domain.model.comment.CommentResponseDTO

interface CommentRepository {
    suspend fun getComments(
        postId: String,
        parentId: String? = null,
        authorId: String? = null,
        cursor: String? = null
    ): Result<CommentResponseDTO, DataError.NetworkError>
}