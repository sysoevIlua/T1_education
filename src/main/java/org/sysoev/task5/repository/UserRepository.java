package org.sysoev.task5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sysoev.task5.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

}
