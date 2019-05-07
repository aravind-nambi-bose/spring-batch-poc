package com.bose.batch.batch.config;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.stereotype.Component;

@Component
public class NoPersistenceBatchConfigurer extends DefaultBatchConfigurer {

  @Override
  public void setDataSource(DataSource dataSource) {
    //Defaults to in-memory database for storing batch related information
  }

}
