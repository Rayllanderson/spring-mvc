package com.rayllanderson.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rayllanderson.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
    
    @Query("select a from Address a where a.people.id = :id")
    public List<Address> findAddressesByPeopleId(@Param("id") Long peopleId);
    
    /**
     * seleciona apenas o atributo id.
     * @param peopleId
     * @return apenas o atributo ID
     */
    @Query("select NEW Address(a.id) from Address a where a.people.id = :id")
    public List<Address> addressesIdByPeopleId(@Param("id") Long peopleId);
    
}
