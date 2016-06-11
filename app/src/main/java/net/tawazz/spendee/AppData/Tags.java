package net.tawazz.spendee.AppData;

/**
 * Created by tawanda on 11/06/2016.
 */
public class Tags {
    private String tagName,tagIcon;
    private int tagId;

    public Tags(String tagName,String tagIcon, int tagId) {
        this.tagName = tagName;
        this.tagId = tagId;
        this.tagIcon = tagIcon;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagIcon() {
        return tagIcon;
    }

    public void setTagIcon(String tagIcon) {
        this.tagIcon = tagIcon;
    }
}
