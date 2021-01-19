package com.rayllanderson.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rayllanderson.entities.Telephone;

public interface TelephoneRepository extends JpaRepository<Telephone, Long>{
    
    @Query("select t from Telephone t where t.people.id = :id")
    public List<Telephone> FindPhonesByPeopleId(@Param("id") Long peopleId);
}
