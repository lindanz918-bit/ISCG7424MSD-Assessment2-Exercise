package cs.linda.iscg7424assessment2exercise.exercise1;

public class Contact {
    private String first;
    private String last;
    private String email;
    private String phone;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Contact() {}

    public Contact(String first, String last, String email, String phone) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.phone = phone;
    }
}







