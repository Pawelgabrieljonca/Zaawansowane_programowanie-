    package pl.edu.reflectioneditor;

public class SampleBean {
    private String name;
    private int age;
    private boolean active;
    private String descriptionText;

    public SampleBean() {
        this.name = "Paweł";
        this.age = 20;
        this.active = true;
        this.descriptionText = "Opis apki";
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public boolean getActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getDescriptionText() { return descriptionText; }
    public void setDescriptionText(String descriptionText) { this.descriptionText = descriptionText; }
}

