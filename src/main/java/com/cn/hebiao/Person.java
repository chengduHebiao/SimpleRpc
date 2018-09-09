
package com.cn.hebiao;

/**
 * @author hebiao
 * @version $Id:Person.java, v0.1 2018/9/3 14:24 hebiao Exp $$
 */
public class Person implements Comparable {

  private Long id;
  private String name;

  public Person() {

  }

  public Person(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Person{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }

  @Override
  public int compareTo(Object o) {
    if (o == null) {
      throw new IllegalArgumentException("");
    }
    Person person = (Person) o;
    return this.getId().compareTo(person.getId());
  }
}

