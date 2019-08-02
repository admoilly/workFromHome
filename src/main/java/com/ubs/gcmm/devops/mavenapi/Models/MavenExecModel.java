package com.ubs.gcmm.devops.mavenapi.Models;

import javax.persistence.*;

@Entity
@Table(name = "Maven_execution_audit")
public class MavenExecModel {
    private int id;
    private String executionId;
    private String pomLocation;
    private String settingLocation;
    private String additionalParams;
    private String execCommand;
    private String status;

    public MavenExecModel() {
    }

    public MavenExecModel(String pomLocation, String settingLocation, String additionalParams, String execCommand) {
        this.pomLocation = pomLocation;
        this.settingLocation = settingLocation;
        this.additionalParams = additionalParams;
        this.execCommand = execCommand;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getPomLocation() {
        return pomLocation;
    }

    public void setPomLocation(String pomLocation) {
        this.pomLocation = pomLocation;
    }

    public String getSettingLocation() {
        return settingLocation;
    }

    public void setSettingLocation(String settingLocation) {
        this.settingLocation = settingLocation;
    }

    public String getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(String additionalParams) {
        this.additionalParams = additionalParams;
    }

    public String getExecCommand() {
        return execCommand;
    }

    public void setExecCommand(String execCommand) {
        this.execCommand = execCommand;
    }
}
