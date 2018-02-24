package org.goldenworkshop.trenden.view.rest;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Foo implements Serializable {
    private String name;
    private int age;

    public Foo(String a, int i) {
        this.name = a;
        this.age = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
