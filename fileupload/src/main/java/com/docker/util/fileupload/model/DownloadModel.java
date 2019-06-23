package com.docker.util.fileupload.model;

public class DownloadModel {
    private String jarDownloadLink;
    private String propertyFileDownloadLink;

    public DownloadModel(String jarDownloadLink, String propertyFileDownloadLink) {
        this.jarDownloadLink = jarDownloadLink;
        this.propertyFileDownloadLink = propertyFileDownloadLink;
    }

    public String getJarDownloadLink() {
        return jarDownloadLink;
    }

    public void setJarDownloadLink(String jarDownloadLink) {
        this.jarDownloadLink = jarDownloadLink;
    }

    public String getPropertyFileDownloadLink() {
        return propertyFileDownloadLink;
    }

    public void setPropertyFileDownloadLink(String propertyFileDownloadLink) {
        this.propertyFileDownloadLink = propertyFileDownloadLink;
    }
}
