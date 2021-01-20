package com.rayllanderson.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rayllanderson.entities.Telephone;

public interface TelephoneRepository extends JpaRepository<Telephone, Long>{
    
    @Query("select t from Telephone t where t.people.id = :id")
    public List<Telephone> findPhonesByPeopleId(@Param("id") Long peopleId);
    
    @Query("select t from Telephone t where t.number like concat('%', :number, '%') and t.people.id = :peopleId")
    public List<Telephone> findByNumber(@Param("number") String number, @Param("peopleId") Long peopleId);
}
