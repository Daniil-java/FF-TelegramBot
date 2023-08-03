package ru.FindFood.dtos;

import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode
public class PersonDto {
    private Long id;
    private String username;
    private String sex;
    private LocalDate birthdate;
    private Integer weight;
    private Integer height;
    private String activity_title;
    private String goal_title;
    private PersonInfoDto info_dto;

  public PersonDto() {
    }

    public PersonDto(Long id, String username, String sex, LocalDate birthdate, Integer weight, Integer height, String activity_title, String goal_title, PersonInfoDto info_dto) {
        this.id = id;
        this.username = username;
        this.sex = sex;
        this.birthdate = birthdate;
        this.weight = weight;
        this.height = height;
        this.activity_title = activity_title;
        this.goal_title = goal_title;
        this.info_dto = info_dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }

    public String getGoal_title() {
        return goal_title;
    }

    public void setGoal_title(String goal_title) {
        this.goal_title = goal_title;
    }

    public PersonInfoDto getInfo_dto() {
        return info_dto;
    }

    public void PersonInfoDto(PersonInfoDto infoDto) {
        this.info_dto = infoDto;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", sex=" + sex +
                ", birthdate=" + birthdate +
                ", weight=" + weight +
                ", height=" + height +
                ", activity_title=" + activity_title +
                ", goal_title=" + goal_title +
                ", info_id=" + info_dto +
                '}';
    }
}
