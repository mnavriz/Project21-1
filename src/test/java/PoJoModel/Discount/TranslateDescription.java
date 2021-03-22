package PoJoModel.Discount;

public class TranslateDescription {
    private static String lang;
    private static String defaultLang;
    private static String data;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getDefaultLang() {
        return defaultLang;
    }

    public void setDefaultLang(String defaultLang) {
        this.defaultLang = defaultLang;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TranslateDescription{" +
                "lang='" + lang + '\'' +
                ", defaultLang='" + defaultLang + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
