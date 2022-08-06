package jp.co.smartware.infrastructure.form;

import java.io.File;

import org.jboss.resteasy.reactive.RestForm;

public class FileUploadForm {
    @RestForm
    public File file;
}
