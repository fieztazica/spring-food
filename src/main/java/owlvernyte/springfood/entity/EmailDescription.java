package owlvernyte.springfood.entity;

public class EmailDescription {
    public String email;
    public String name;
    public String subject;
    public String body;

    public EmailDescription() {
    }

    public EmailDescription(String receiver, String name, String subject, String body) {
        this.email = receiver;
        this.name = name;
        this.subject = subject;
        this.body = body;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
