package PoJoModel.Notification;

public class NotificationPoJo {



    private static String id;
    private static String name;
    private static String description;
    private static String type;
    private static String schoolId;
    private static boolean deleted;
    private static boolean active;
    private static boolean sendSMS;
    private static int sendSMSDaysBefore;
    private static String sms;
    private static boolean sendEmail;
    private static int sendEmailDaysBefore;
    private static EmailMessage emailMessage;

    public  String getId() {
        return id;
    }

    public  void setId(String id) {
        NotificationPoJo.id = id;
    }

    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        NotificationPoJo.name = name;
    }

    public  String getDescription() {
        return description;
    }

    public  void setDescription(String description) {
        NotificationPoJo.description = description;
    }

    public  String getType() {
        return type;
    }

    public  void setType(String type) {
        NotificationPoJo.type = type;
    }

    public  String getSchoolId() {
        return schoolId;
    }

    public  void setSchoolId(String schoolId) {
        NotificationPoJo.schoolId = schoolId;
    }

    public  boolean isDeleted() {
        return deleted;
    }

    public  void setDeleted(boolean deleted) {
        NotificationPoJo.deleted = deleted;
    }

    public  boolean isActive() {
        return active;
    }

    public  void setActive(boolean active) {
        NotificationPoJo.active = active;
    }

    public  boolean isSendSMS() {
        return sendSMS;
    }

    public  void setSendSMS(boolean sendSMS) {
        NotificationPoJo.sendSMS = sendSMS;
    }

    public  int getSendSMSDaysBefore() {
        return sendSMSDaysBefore;
    }

    public  void setSendSMSDaysBefore(int sendSMSDaysBefore) {
        NotificationPoJo.sendSMSDaysBefore = sendSMSDaysBefore;
    }

    public  String getSms() {
        return sms;
    }

    public  void setSms(String sms) {
        NotificationPoJo.sms = sms;
    }

    public  boolean isSendEmail() {
        return sendEmail;
    }

    public  void setSendEmail(boolean sendEmail) {
        NotificationPoJo.sendEmail = sendEmail;
    }

    public  int getSendEmailDaysBefore() {
        return sendEmailDaysBefore;
    }

    public  void setSendEmailDaysBefore(int sendEmailDaysBefore) {
        NotificationPoJo.sendEmailDaysBefore = sendEmailDaysBefore;
    }

    public  EmailMessage getEmailMessage() {
        return emailMessage;
    }

    public  void setEmailMessage(EmailMessage emailMessage) {
        NotificationPoJo.emailMessage = emailMessage;
    }


}

