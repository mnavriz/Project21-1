package PoJoModel;

import java.util.HashMap;
import java.util.List;

public class DiscountPoJo {
    private static String id;
    private static String description;
    private static String code;
    private static List<TranslateDescription> translateDescription;
    private static String active;
    private static int priority;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<TranslateDescription> getTranslateDescription() {
        return translateDescription;
    }

    public void setTranslateDescription(List<TranslateDescription> translateDescription) {
        this.translateDescription = translateDescription;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "DiscountPoJo{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", translateDescription=" + translateDescription +
                ", active=" + active +
                ", priority=" + priority +
                '}';
    }
}
