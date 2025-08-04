package com.evcsms.chargestationserver.repository;

import com.evcsms.chargestationserver.model.Connector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectorRepository extends JpaRepository<Connector, Long> {

}
