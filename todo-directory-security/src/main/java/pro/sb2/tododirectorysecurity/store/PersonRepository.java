package pro.sb2.tododirectorysecurity.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pro.sb2.tododirectorysecurity.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    public Person findByEmailIgnoreCase(@Param("email") String email);
}
