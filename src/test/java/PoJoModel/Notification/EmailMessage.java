package PoJoModel.Notification;

public class EmailMessage {
    private static String subject;
    private static String content;
    private static String to;
    private static String cc;
    private static String bcc;

    public  String getSubject() {
        return subject;
    }

    public  void setSubject(String subject) {
        EmailMessage.subject = subject;
    }

    public  String getContent() {
        return content;
    }

    public  void setContent(String content) {
        EmailMessage.content = content;
    }

    public  String getTo() {
        return to;
    }

    public  void setTo(String to) {
        EmailMessage.to = to;
    }

    public  String getCc() {
        return cc;
    }

    public  void setCc(String cc) {
        EmailMessage.cc = cc;
    }

    public  String getBcc() {
        return bcc;
    }

    public  void setBcc(String bcc) {
        EmailMessage.bcc = bcc;
    }


}
