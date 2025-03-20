// DouArea.java
package model;

public class DouArea {
    private int areaId;
    private int parentId;
    private String name;

    public DouArea(int areaId, int parentId, String name) {
        this.areaId = areaId;
        this.parentId = parentId;
        this.name = name;
    }

    public DouArea() {

    }

    // Getters and setters
    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
