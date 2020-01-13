package pictures.taking.washing.helper;

public enum ResourceBundle {
    MESSAGES("resources.messages"), VALIDATIONS("ValidationMessages");
    private String bundleName;

    ResourceBundle(String bundleName) {
        this.bundleName = bundleName;
    }

    public String getBundleName() {
        return bundleName;
    }

    @Override
    public String toString() {
        return bundleName;
    }
}