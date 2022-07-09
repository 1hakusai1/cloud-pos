package jp.co.smartware.boundary.form;

import java.io.File;

import org.jboss.resteasy.reactive.RestForm;

public class FileUploadFormData {

    @RestForm("file")
    public File file;
}
