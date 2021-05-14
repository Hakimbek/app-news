package uz.pdp.appnews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appnews.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
