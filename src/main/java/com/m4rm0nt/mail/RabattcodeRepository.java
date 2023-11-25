package com.m4rm0nt.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RabattcodeRepository extends JpaRepository<Rabattcode, Long> {
}