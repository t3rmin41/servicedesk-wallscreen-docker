package com.domain.servicedesk.wallscreen.scheduled;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DatabaseBackupTask {

  @Value("${spring.profiles.active}")
  private String activeProfile;
  
  private static final Logger log = LoggerFactory.getLogger(DatabaseBackupTask.class);

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

  //@Scheduled(cron="0 */5 * * * *")
  //public void reportCurrentTime() {
  //  log.info("The time is now {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
  //}

  //@Scheduled(cron="0 */5 * * * *") //every 5 minutes
  /*
  public void makeDatabaseFileBackup() {
    boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

    String currentDir = System.getProperty("user.dir");
    
    try {
      ProcessBuilder builder = new ProcessBuilder();
      
      if (isWindows) {
          //builder.command("cmd.exe", "/c", "dir");
          //builder.command("cmd.exe", "/c", "cp ./db/"+activeProfile+"/bin.mv.db ./db-backup/"+activeProfile+"/"+dateFormat+"_bin.mv.db");
          //builder.command("cmd.exe", "/c", "powershell -file scripts\backup-database.ps1 " + activeProfile);
          //builder.command("cmd.exe", "/c", "xcopy ./db/"+activeProfile+"/bin.mv.db ./db-backup/"+activeProfile+"/"+dateFormat+"_bin.mv.db");
      } else {
          //builder.command("sh", "-c", "ls");
          builder.command("sh", "-c", "scripts/backup-database.sh " + activeProfile);
      }

      Process process = builder.start();
      
      StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
      
      Executors.newSingleThreadExecutor().submit(streamGobbler);
      
      int exitCode = process.waitFor();
      assert exitCode == 0;

      //Process process;
      //if (isWindows) {
      //    process = Runtime.getRuntime().exec(String.format("cmd.exe /c powershell -file scripts\backup-database.ps1 %s", activeProfile));
      //} else {
      //    process = Runtime.getRuntime().exec(String.format("sh -c scripts/backup-database.sh %s", activeProfile));
      //}
      //StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
      //Executors.newSingleThreadExecutor().submit(streamGobbler);
      //int exitCode = process.waitFor();
      //assert exitCode == 0;
    } catch (InterruptedException | IOException e) {
      log.error(e.getMessage());
    } 
  }
  /**/

}
