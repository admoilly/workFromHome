package com.ubs.gcmm.devops.mavenapi.Utils;

import com.ubs.gcmm.devops.mavenapi.Models.MavenExecModel;
import com.ubs.gcmm.devops.mavenapi.Repository.MavenExecRepository;
import org.apache.maven.cli.MavenCli;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MavenImpl {
    @Autowired
    private MavenExecRepository mavenExecRepository;

    private boolean runMavenCommand(MavenExecModel execModel) {
        System.setProperty("maven.multiModuleProjectDirectory", "true");
        String[] CommandArray = execModel.getExecCommand().split(",");
        List<String> l2 = new ArrayList<>();
        if (execModel.getAdditionalParams() != null) {
            String[] addtionalParamArray = execModel.getAdditionalParams().split(";");
            l2.addAll(Arrays.asList(addtionalParamArray));
        }
        if (execModel.getSettingLocation() != null) {
            l2.add("-s");
            l2.add(execModel.getSettingLocation());
        }
        l2.addAll(Arrays.asList(CommandArray));
        String[] item = l2.toArray(new String[0]);
        String projectLocation = new File(execModel.getPomLocation()).getAbsolutePath();
        if (projectLocation.contains("pom")) {
            projectLocation = (new File(execModel.getPomLocation())).getParent();
        }
        MavenCli maven = new MavenCli();
        int exitCode = maven.doMain(item, projectLocation, System.out, System.out);
        return exitCode == 0;
    }

    @JmsListener(destination = "maven.trigger.queue")
    public void receiveQueue(MavenExecModel execModel) {
        String exectutionId=hashCode(String.format("%s%d", execModel.getClass().toString(), ZonedDateTime.now().toInstant().toEpochMilli()));
        execModel.setExecutionId(exectutionId);
        System.out.printf("Executing : %s", exectutionId);
        if(runMavenCommand(execModel)){
            execModel.setStatus(MavenAPIConstant.MAVEN_EXECUTION_STATUS_SUCCESS);
        }else {
            execModel.setStatus(MavenAPIConstant.MAVEN_EXECUTION_STATUS_FAILURE);
        }
        mavenExecRepository.save(execModel);
    }

    private String hashCode(String value){
        String hashvalue = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            hashvalue = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return hashvalue;
    }
}
