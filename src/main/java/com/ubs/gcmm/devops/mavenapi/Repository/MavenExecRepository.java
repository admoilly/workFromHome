package com.ubs.gcmm.devops.mavenapi.Repository;

import com.ubs.gcmm.devops.mavenapi.Models.MavenExecModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MavenExecRepository extends JpaRepository<MavenExecModel, Long> {

}