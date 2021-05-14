package uz.pdp.appnews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appnews.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
