package com.vscoding.tutorial.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogService {

  @Scheduled(fixedDelay = 7000)
  public void log_every_seven_seconds() throws Exception {
    selectLog();
  }

  @Scheduled(fixedDelay = 5000)
  public void log_every_fife_seconds() throws Exception {
    selectLog();
  }

  @Scheduled(fixedDelay = 3000)
  public void log_every_three_seconds() throws Exception {
    selectLog();
  }

  private void selectLog(){
    var logSelector = (int) (Math.random()*7);
    switch (logSelector){
      case 0:{
        log.info("Operation: customer login successful");
        break;
      }
      case 1:{
        log.info("Sales: product purchased");
        break;
      }
      case 3:{
        log.warn("Sales: product not in stock");
        break;
      }
      case 4:{
        log.error("Operation: customer registration error");
        break;
      }
      case 5:{
        log.error("Operation: add to card failed");
        break;
      }
      case 6: {
        try{
          throw new RuntimeException("Something gone wrong");
        }catch (Exception e){
          log.error("Exception: {}",e.getMessage(),e);
        }
        break;
      }
    }
  }
}
