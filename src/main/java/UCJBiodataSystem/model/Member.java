package UCJBiodataSystem.model;

import javax.persistence.*;

@Entity
@Table(name = "Member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="gender")
    private String gender;

    @Column(name="date_of_birth")
    private String dateOfBirth;

    @Column(name="email", nullable=false, unique = true)
    private String email;

    @Column(name="phone_number", nullable=false, unique = true)
    private String phoneNumber;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "department")
    private String department;

    @Column(name = "hall_of_residence")
    private String hallOfRecidence;

    @Column(name = "lpo")
    private String lpo;

    @Column(name = "year_of_induction")
    private String yearOfInduction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getHallOfRecidence() {
        return hallOfRecidence;
    }

    public void setHallOfRecidence(String hallOfRecidence) {
        this.hallOfRecidence = hallOfRecidence;
    }

    public String getLpo() {
        return lpo;
    }

    public void setLpo(String lpo) {
        this.lpo = lpo;
    }

    public String getYearOfInduction() {
        return yearOfInduction;
    }

    public void setYearOfInduction(String yearOfInduction) {
        this.yearOfInduction = yearOfInduction;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", faculty='" + faculty + '\'' +
                ", department='" + department + '\'' +
                ", hallOfRecidence='" + hallOfRecidence + '\'' +
                ", lpo='" + lpo + '\'' +
                ", yearOfInduction='" + yearOfInduction + '\'' +
                '}';
    }
}
