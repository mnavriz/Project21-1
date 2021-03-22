package PoJoModel.Document;

import com.sun.xml.bind.v2.schemagen.xmlschema.List;

public class DocumentPoJo {

    private static String id;
    private static boolean active;
    private static String attachmentStages;
    private static String description;
    private static String name;
    private static boolean required;
    private static String schoolId;
    private static boolean useCamera;
    private static List translateName;

    public  String getId() {
        return id;
    }

    public  void setId(String id) {
        DocumentPoJo.id = id;
    }

    public  boolean isActive() {
        return active;
    }

    public  void setActive(boolean active) {
        DocumentPoJo.active = active;
    }

    public  String getAttachmentStages() {
        return attachmentStages;
    }

    public  void setAttachmentStages(String attachmentStages) {
        DocumentPoJo.attachmentStages = attachmentStages;
    }

    public  String getDescription() {
        return description;
    }

    public  void setDescription(String description) {
        DocumentPoJo.description = description;
    }

    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        DocumentPoJo.name = name;
    }

    public  boolean isRequired() {
        return required;
    }

    public  void setRequired(boolean required) {
        DocumentPoJo.required = required;
    }

    public  String getSchoolId() {
        return schoolId;
    }

    public  void setSchoolId(String schoolId) {
        DocumentPoJo.schoolId = schoolId;
    }

    public  boolean isUseCamera() {
        return useCamera;
    }

    public  void setUseCamera(boolean useCamera) {
        DocumentPoJo.useCamera = useCamera;
    }

    public static List getTranslateName() {
        return translateName;
    }

    public static void setTranslateName(List translateName) {
        DocumentPoJo.translateName = translateName;
    }
}
