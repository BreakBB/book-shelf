package de.codecentric.bb.cover;

import org.springframework.data.repository.CrudRepository;

public interface CoverRepository extends CrudRepository<Cover, Long> {

    Cover findCoverByIsbn(String isbn);
}
