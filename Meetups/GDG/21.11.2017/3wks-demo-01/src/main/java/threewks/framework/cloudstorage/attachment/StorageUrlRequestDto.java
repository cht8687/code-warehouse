package threewks.framework.cloudstorage.attachment;

public class StorageUrlRequestDto {
    private String type;
    private String name;

    public String getType() {
        return type;
    }

    public StorageUrlRequestDto setType(String type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public StorageUrlRequestDto setName(String name) {
        this.name = name;
        return this;
    }

}
