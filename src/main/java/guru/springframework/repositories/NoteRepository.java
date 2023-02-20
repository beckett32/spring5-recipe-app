package guru.springframework.repositories;

import guru.springframework.domain.Notes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends CrudRepository<Notes, Long> {
}
