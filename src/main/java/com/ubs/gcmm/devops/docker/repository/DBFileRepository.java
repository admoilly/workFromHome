package com.ubs.gcmm.devops.docker.repository;


import com.ubs.gcmm.devops.docker.Model.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

}
